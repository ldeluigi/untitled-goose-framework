package controller.engine.vertx

import java.util.concurrent.atomic.AtomicBoolean

import controller.ViewController
import controller.engine.EventSink
import io.vertx.lang.scala.VertxExecutionContext
import io.vertx.scala.core.Vertx
import io.vertx.scala.core.eventbus.DeliveryOptions
import model.entities.DialogContent
import model.entities.runtime.Game
import model.events.GameEvent
import model.events.special.{ExitEvent, NoOpEvent}
import model.rules.operations.Operation
import model.rules.operations.Operation.{DialogOperation, SpecialOperation}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

/**
 * The runtime engine of the game. It can receive events to handle,
 * and can be polled to get the game, which includes the state.
 */
trait GooseEngine {
  def game: Game

  def eventSink: EventSink[GameEvent]

  def stop(): Unit
}

object GooseEngine {

  private class GooseEngineImpl(private val gameMatch: Game, private val controller: ViewController) extends GooseEngine with EventSink[GameEvent] {
    private type DialogDisplay = DialogContent => Future[GameEvent]
    private val vertx: Vertx = Vertx.vertx()
    private val gv = new GooseVerticle(onEvent)
    implicit val vertxExecutionContext: VertxExecutionContext = VertxExecutionContext(
      vertx.getOrCreateContext()
    )
    private val dialogDisplay: DialogDisplay = (dialogContent: DialogContent) => controller.showDialog(dialogContent)
    vertx.eventBus.registerCodec(new GameEventMessageCodec)
    Await.result(vertx.deployVerticleFuture(gv), Duration.Inf)
    private var stack: Seq[Operation] = Seq()
    private val stopped: AtomicBoolean = new AtomicBoolean(false)

    override def accept(event: GameEvent): Unit = {
      vertx.eventBus().send(gv.eventAddress, Some(event), DeliveryOptions().setCodecName(GameEventMessageCodec.name))
    }

    def executeOperation(): Unit = {
      if (stopped.get()) return
      val op: Operation = stack.head
      stack = stack.tail
      op.execute(gameMatch.currentState)
      op match {
        case operation: SpecialOperation =>
          operation match {
            case o: DialogOperation =>
              stopped.set(true)
              dialogDisplay(o.content).onComplete(res => {
                stopped.set(false)
                res match {
                  case Failure(_) => executeOperation()
                  case Success(event) => accept(event)
                }
              })
          }
        case _ => Unit
      }
      if (stopped.get()) return
      controller.update(gameMatch.currentState)
      if (stack.nonEmpty) {
        stack = gameMatch.stateBasedOperations ++ stack
        executeOperation()
      }
    }

    override def game: Game = gameMatch

    override def eventSink: EventSink[GameEvent] = this

    override def stop(): Unit = vertx.close()

    private def onEvent(event: GameEvent): Unit = {
      event match {
        case ExitEvent => controller.close()
        case NoOpEvent => executeOperation()
        case _ =>
          controller.logAsyncEvent(event)
          if (stack.isEmpty) {
            stack = stack :+ gameMatch.cleanup
          }
          stack = Operation.trigger(event) +: stack
          executeOperation()
      }
    }
  }

  /**
   * This factory creates a GooseEngine actor that encapsulates a Vert.x verticle,
   * that is an autonomous control flow, to work asynchronously as a game controller.
   * @param status The Game, which is modified in-place.
   * @param controller The ViewController. Its interactions with the engine are managed
   *                   with concurrency in mind.
   * @return The GooseEngine implemented using the Vert.x library.
   */
  def apply(status: Game, controller: ViewController): GooseEngine = new GooseEngineImpl(status, controller)
}
