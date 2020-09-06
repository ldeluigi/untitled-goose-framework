package untitled.goose.framework.dsl.events.words

object EventType extends Enumeration {

  type EventType = Value

  val gameEvent, playerEvent, tileEvent = Value
}
