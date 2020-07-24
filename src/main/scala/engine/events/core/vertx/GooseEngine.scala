package engine.events.core.vertx

import engine.`match`.Match
import engine.events.core.EventSink
import engine.events.root.{GameEvent, PlayerEvent, TileEvent}
import io.vertx.lang.scala.VertxExecutionContext
import io.vertx.scala.core.Vertx
import io.vertx.scala.core.eventbus.DeliveryOptions

trait GooseEngine {
  def currentMatch: Match
  def eventSink: EventSink[GameEvent]
  def stop(): Unit
}

object GooseEngine {


  private class GooseEngineImpl(private val status: Match) extends GooseEngine with EventSink[GameEvent] {

    private val vertx: Vertx = Vertx.vertx()
    implicit val vertxExecutionContext: VertxExecutionContext = VertxExecutionContext(
      vertx.getOrCreateContext()
    )
    private val gv = new GooseVerticle(onEvent)
    vertx.eventBus.registerCodec(new GameEventMessageCodec)
    vertx.deployVerticle(gv)

    override def accept(event: GameEvent): Unit =
      vertx.eventBus().send(gv.eventAddress, Some(event), DeliveryOptions().setCodecName(GameEventMessageCodec.name))

    private def onEvent(event: GameEvent) : Unit = event match {
      case PlayerEvent(player) => println(player)// TODO update history in some way
      case TileEvent(tile) => println(tile)
      case x => println(x)
    }

    override def currentMatch: Match = status

    override def eventSink: EventSink[GameEvent] = this

    override def stop(): Unit = vertx.close()
  }

  def apply(status: Match): GooseEngine = new GooseEngineImpl(status)
}
