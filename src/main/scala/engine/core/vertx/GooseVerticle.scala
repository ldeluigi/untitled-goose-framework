package engine.core.vertx

import engine.events.root.GameEvent
import io.vertx.lang.scala.ScalaVerticle

private[vertx] class GooseVerticle(val handler: GameEvent => Unit) extends ScalaVerticle {
  def eventAddress: String = "GameEvent"

  override def start(): Unit = {
    println("Goose Engine - Vert.x Verticle: start")
    vertx.eventBus().consumer[GameEvent](eventAddress)
      .handler(e => handler(e.body()))
  }

  override def stop(): Unit = {
    println("Goose Engine - Vert.x Verticle: stop")
  }
}
