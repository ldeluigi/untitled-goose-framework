package untitled.goose.framework.controller.engine.vertx

import java.util.concurrent.atomic.AtomicBoolean

import io.vertx.lang.scala.VertxExecutionContext
import io.vertx.scala.core.Vertx
import io.vertx.scala.core.eventbus.DeliveryOptions
import untitled.goose.framework.controller.ViewController
import untitled.goose.framework.controller.engine.{EventSink, GooseEngine}
import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.entities.runtime.{CloneGameState, Game}
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.special.{ActionEvent, ExitEvent, NoOpEvent}
import untitled.goose.framework.model.rules.operations.Operation
import untitled.goose.framework.model.rules.operations.Operation.{DialogOperation, SpecialOperation}

import scala.annotation.tailrec
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

/** Offers GooseEngine implementation with Vert.x library. */
object VertxGooseEngine {

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

    @tailrec
    private def executeOperation(): Unit = {
      if (stopped.get()) return
      if (stack.nonEmpty) {
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
                    case Failure(_) => accept(NoOpEvent)
                    case Success(event) => accept(event)
                  }
                })
            }
          case _ => Unit
        }
        if (stopped.get()) return
      }
      controller.update(CloneGameState(gameMatch.currentState), gameMatch.availableActions)
      if (stack.nonEmpty) {
        stack = gameMatch.stateBasedOperations() ++ stack
        executeOperation()
      }
    }

    override def eventSink: EventSink[GameEvent] = this

    override def stop(): Unit = vertx.close()

    @tailrec
    private def onEvent(event: GameEvent): Unit = {
      event match {
        case ExitEvent => controller.close()
        case NoOpEvent => executeOperation()
        case ActionEvent(action) => onEvent(action.trigger(gameMatch.currentState))
        case _ =>
          if (stack.isEmpty) stack :+= gameMatch.cleanup()
          stack +:= Operation.trigger(event)
          executeOperation()
      }
    }

    override def callViewUpdate(): Unit = accept(NoOpEvent)
  }

  /**
   * This factory creates a GooseEngine actor that encapsulates a Vert.x verticle,
   * that is an autonomous control flow, to work asynchronously as a game untitled.goose.framework.controller.
   *
   * @param status     The Game, which is modified in-place.
   * @param controller The ViewController. Its interactions with the engine are managed
   *                   with concurrency in mind.
   * @return The GooseEngine implemented using the Vert.x library.
   */
  def apply(status: Game, controller: ViewController): GooseEngine = new GooseEngineImpl(status, controller)

}
