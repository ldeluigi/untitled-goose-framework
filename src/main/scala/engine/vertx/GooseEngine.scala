package engine.vertx

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}

import engine.events.EventSink
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec
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
  vertx.eventBus.registerDefaultCodec(classOf[GameEvent], new GameEventMessageCodec)
  vertx.deployVerticle(gv)

  override def accept(event: GameEvent): Unit =
    vertx.eventBus().sendFuture[AnyRef](gv.eventAddress, Some(event))
}

private class GameEventMessageCodec extends MessageCodec[GameEvent, GameEvent] {
  override def encodeToWire(buffer: Buffer, s: GameEvent): Unit = {
    val stream: ByteArrayOutputStream = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(stream)
    oos.writeObject(s)
    oos.close
    buffer.appendBytes(stream.toByteArray)
  }

  override def decodeFromWire(pos: Int, buffer: Buffer): GameEvent = {
    val bytes = buffer.getBytes
    val ois = new ObjectInputStream(new ByteArrayInputStream(bytes))
    val value = ois.readObject
    ois.close
    value.asInstanceOf[GameEvent]
  }

  override def transform(s: GameEvent): GameEvent = s

  override def name(): String = "GameEvent_Codec"

  override def systemCodecID(): Byte = -1
}