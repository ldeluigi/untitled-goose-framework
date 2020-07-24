package engine.events.core.vertx

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}

import engine.events.root.GameEvent
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec

private[vertx] class GameEventMessageCodec extends MessageCodec[GameEvent, GameEvent] {
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

  override def name(): String = GameEventMessageCodec.name

  override def systemCodecID(): Byte = -1
}

private[vertx] object GameEventMessageCodec {
  def name: String = "GameEvent_Codec"
}
