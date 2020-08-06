package engine.events.core.vertx

import engine.`match`.Match
import engine.events.core.{DialogDisplayer, EventSink}
import engine.events.root.GameEvent
import io.vertx.lang.scala.VertxExecutionContext
import io.vertx.scala.core.Vertx
import io.vertx.scala.core.eventbus.DeliveryOptions
import model.entities.DialogContent
import model.rules.operations.{DialogOperation, Operation, SpecialOperation, TerminateTurnOperation}
import view.GooseController

import scala.util.{Failure, Success}

trait GooseEngine {
  def currentMatch: Match

  def eventSink: EventSink[GameEvent]

  def stop(): Unit
}

object GooseEngine {


  private class GooseEngineImpl(private val gameMatch: Match, private val controller: GooseController) extends GooseEngine with EventSink[GameEvent] {

    private var stack: Seq[Operation] = Seq()

    private val vertx: Vertx = Vertx.vertx()
    implicit val vertxExecutionContext: VertxExecutionContext = VertxExecutionContext(
      vertx.getOrCreateContext()
    )
    private val gv = new GooseVerticle(onEvent)
    vertx.eventBus.registerCodec(new GameEventMessageCodec)
    vertx.deployVerticle(gv)

    val dialogDisplayer: DialogDisplayer = (dialogContent: DialogContent) => controller.showDialog(dialogContent)

    override def accept(event: GameEvent): Unit = {
      vertx.eventBus().send(gv.eventAddress, Some(event), DeliveryOptions().setCodecName(GameEventMessageCodec.name))
    }

    var stopped = false

    private def onEvent(event: GameEvent): Unit = {
      //controller.logEvent(event) TODO FRA Decomment when implemented
      println(event.name + " " + event.turn)
      gameMatch.submitEvent(event)
      stack = gameMatch.stateBasedOperations ++ stack
      executeOperation()
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
                dialogDisplayer.display(o.content).onComplete(res => {
                  stopped = false
                  res match {
                    case Failure(_) => executeOperation()
                    case Success(event) => onEvent(event)
                  }
                })
              case _: TerminateTurnOperation =>
                stack = Seq()
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
  }

  def apply(status: Match, controller: GooseController): GooseEngine = new GooseEngineImpl(status, controller)
}
