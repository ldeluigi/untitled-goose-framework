package engine.vertx

import engine.events.EventSink
import io.vertx.lang.scala.VertxExecutionContext
import io.vertx.scala.core.Vertx
import model.GameEvent


object GooseEngine {
  def apply(): GooseEngine = new GooseEngine()
}

class GooseEngine extends EventSink[GameEvent] {
  val vertx: Vertx = Vertx.vertx()
  implicit val vertxExecutionContext: VertxExecutionContext = VertxExecutionContext(
    vertx.getOrCreateContext()
  )
  val gv = new GooseVerticle
  vertx.deployVerticle(gv)

  override def accept(event: GameEvent): Unit =
    vertx.eventBus().sendFuture(gv.eventAddress, Some(event))
    .map(res => println(res.body()))
}
