package engine.core.vertx

import java.util.concurrent.atomic.AtomicBoolean

import engine.core.{DialogDisplay, EventSink}
import engine.events.{ExitEvent, GameEvent, NoOpEvent}
import io.vertx.lang.scala.VertxExecutionContext
import io.vertx.scala.core.Vertx
import io.vertx.scala.core.eventbus.DeliveryOptions
import model.entities.DialogContent
import model.game.Game
import model.rules.operations.Operation
import model.rules.operations.Operation.{DialogOperation, SpecialOperation}
import view.GooseController

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.{Failure, Success}

trait GooseEngine {
  def currentMatch: Game

  def eventSink: EventSink[GameEvent]

  def stop(): Unit
}

object GooseEngine {


  def apply(status: Game, controller: GooseController): GooseEngine = new GooseEngineImpl(status, controller)

  private class GooseEngineImpl(private val gameMatch: Game, private val controller: GooseController) extends GooseEngine with EventSink[GameEvent] {
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
      if (!stopped.get()) {
        val op: Operation = stack.head
        stack = stack.tail
        op.execute(gameMatch.currentState)
        op match {
          case operation: SpecialOperation =>
            operation match {
              case o: DialogOperation =>
                stopped.set(true)
                dialogDisplay.display(o.content).onComplete(res => {
                  stopped.set(false)
                  res match {
                    case Failure(_) => executeOperation()
                    case Success(event) => accept(event)
                  }
                })
            }
          case _ => Unit
        }
        controller.update(gameMatch.currentState)
        if (stack.nonEmpty) {
          stack = gameMatch.stateBasedOperations ++ stack
          executeOperation()
        }
      }
    }

    override def currentMatch: Game = gameMatch

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

}
