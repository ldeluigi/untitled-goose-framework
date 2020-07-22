package engine.vertx

import io.vertx.lang.scala.ScalaVerticle
import model.GameEvent

class GooseVerticle extends ScalaVerticle {
  def eventAddress: String = "GameEvent"

  override def start(): Unit = {
    println("Goose Engine - Vert.x Verticle: start")
    vertx.eventBus().consumer[GameEvent](eventAddress)
      .handler(e => e.body() match {
        case _ => e.reply("ok")
      })
  }

  override def stop(): Unit = {
    println("Goose Engine - Vert.x Verticle: stop")
  }
}
