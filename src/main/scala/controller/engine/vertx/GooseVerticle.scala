package controller.engine.vertx

import io.vertx.lang.scala.ScalaVerticle
import model.events.GameEvent

/** A GooseVerticle is a ScalaVerticle that takes the event handler as input. */
private[vertx] class GooseVerticle(val handler: GameEvent => Unit) extends ScalaVerticle {
  override def start(): Unit = {
    println("Goose Engine - Vert.x Verticle: start")
    vertx.eventBus().consumer[GameEvent](eventAddress)
      .handler(e => handler(e.body()))
  }

  /** Exposes the event address string. */
  def eventAddress: String = "GameEvent"

  override def stop(): Unit = {
    println("Goose Engine - Vert.x Verticle: stop")
  }
}
