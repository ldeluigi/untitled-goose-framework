package engine.events.core.vertx

import engine.`match`.Match
import engine.events.core.EventSink
import engine.events.root.GameEvent
import io.vertx.lang.scala.VertxExecutionContext
import io.vertx.scala.core.Vertx
import io.vertx.scala.core.eventbus.DeliveryOptions
import model.rules.operations.Operation
import view.{ApplicationController, GooseController}

trait GooseEngine {
  def currentMatch: Match

  def eventSink: EventSink[GameEvent]

  def stop(): Unit
}

object GooseEngine {


  private class GooseEngineImpl(private val status: Match, private val controller: GooseController) extends GooseEngine with EventSink[GameEvent] {

    private var stack: Seq[Operation] = Seq()

    private val vertx: Vertx = Vertx.vertx()
    implicit val vertxExecutionContext: VertxExecutionContext = VertxExecutionContext(
      vertx.getOrCreateContext()
    )
    private val gv = new GooseVerticle(onEvent)
    vertx.eventBus.registerCodec(new GameEventMessageCodec)
    vertx.deployVerticle(gv)

    override def accept(event: GameEvent): Unit = {
      vertx.eventBus().send(gv.eventAddress, Some(event), DeliveryOptions().setCodecName(GameEventMessageCodec.name))
    }


    private def onEvent(event: GameEvent): Unit = {
      //controller.logEvent(event) TODO FRA Decomment when implemented
      println(event.name + " " + event.turn)
      status.submitEvent(event)
      stack = status.stateBasedOperations ++ stack
      while (stack nonEmpty) {
        val op = stack.head
        stack = stack.tail
        println(op)
        op.execute(status.currentState, stackSolver)
        controller.update(status.currentState)
      }
    }

    override def currentMatch: Match = status

    override def eventSink: EventSink[GameEvent] = this

    override def stop(): Unit = vertx.close()

    private object stackSolver extends EventSink[GameEvent] {
      override def accept(event: GameEvent): Unit = onEvent(event)
    }

  }

  def apply(status: Match, controller: GooseController): GooseEngine = new GooseEngineImpl(status, controller)
}
