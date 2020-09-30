package untitled.goose.framework.controller.engine.vertx

import io.vertx.lang.scala.{ScalaLogger, ScalaVerticle}
import untitled.goose.framework.model.events.GameEvent

/** A GooseVerticle is a ScalaVerticle that takes the event handler as input. */
private[vertx] class GooseVerticle(val handler: GameEvent => Unit) extends ScalaVerticle {
  val logger: ScalaLogger = LoggerUtils.logger
  override def start(): Unit = {
    logger.info("Vert.x Verticle: start")
    vertx.eventBus().consumer[GameEvent](eventAddress)
      .handler(e => handler(e.body()))
  }

  /** Exposes the event address string. */
  def eventAddress: String = "GameEvent"

  override def stop(): Unit = {
    logger.info("Vert.x Verticle: stop")
  }
}
