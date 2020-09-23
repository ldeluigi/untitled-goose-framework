package untitled.goose.framework.controller.engine.vertx

import io.vertx.lang.scala.ScalaLogger

object LoggerUtils {

  private val log: ScalaLogger =
    ScalaLogger.getLogger("untitled.goose.framework.GooseEngine")

  private[vertx] def logger: ScalaLogger = log
}
