package engine.core.vertx

import engine.`match`.Match
import engine.core.{DialogDisplay, EventSink}
import engine.events.root.{ExitEvent, GameEvent, NoOpEvent}
import io.vertx.lang.scala.VertxExecutionContext
import io.vertx.scala.core.Vertx
import io.vertx.scala.core.eventbus.DeliveryOptions
import model.entities.DialogContent
import model.rules.operations.Operation
import model.rules.operations.SpecialOperation.{DialogOperation, SpecialOperation}
import view.GooseController

import scala.util.{Failure, Success}

trait GooseEngine {
  def currentMatch: Match

  def eventSink: EventSink[GameEvent]

  def stop(): Unit
}

object GooseEngine {


  def apply(status: Match, controller: GooseController): GooseEngine = new GooseEngineImpl(status, controller)

  private class GooseEngineImpl(private val gameMatch: Match, private val controller: GooseController) extends GooseEngine with EventSink[GameEvent] {
    private val vertx: Vertx = Vertx.vertx()
    private val gv = new GooseVerticle(onEvent)
    implicit val vertxExecutionContext: VertxExecutionContext = VertxExecutionContext(
      vertx.getOrCreateContext()
    )
    private val dialogDisplay: DialogDisplay = (dialogContent: DialogContent) => controller.showDialog(dialogContent)
    vertx.eventBus.registerCodec(new GameEventMessageCodec)
    vertx.deployVerticle(gv)
    private var stack: Seq[Operation] = Seq()
    private var stopped = false

    override def accept(event: GameEvent): Unit = {
      vertx.eventBus().send(gv.eventAddress, Some(event), DeliveryOptions().setCodecName(GameEventMessageCodec.name))
    }

    def executeOperation(): Unit = {
      if (!stopped && stack.nonEmpty) {
        val op = stack.head
        stack = stack.tail
        op.execute(gameMatch.currentState, onEvent)
        op match {
          case operation: SpecialOperation =>
            operation match {
              case o: DialogOperation =>
                stopped = true
                dialogDisplay.display(o.content).onComplete(res => {
                  stopped = false
                  res match {
                    case Failure(_) => executeOperation()
                    case Success(event) => onEvent(event)
                  }
                })
              //              case _: TerminateTurnOperation =>
              //                stack = Seq()
            }
          case _ => Unit
        }
        controller.update(gameMatch.currentState)
        executeOperation()
      }
    }

    override def currentMatch: Match = gameMatch

    override def eventSink: EventSink[GameEvent] = this

    override def stop(): Unit = vertx.close()

    private def onEvent(event: GameEvent): Unit = {
      event match {
        case ExitEvent => controller.close()

        case NoOpEvent => executeOperation()

        case _ => controller.logEvent(event)
          if (stack.isEmpty) {
            stack = stack :+ gameMatch.cleanup
          }
          gameMatch.submitEvent(event)
          stack = gameMatch.stateBasedOperations ++ stack
          executeOperation()
      }

    }
  }

}
