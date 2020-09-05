package dsl.words.event

object EventType extends Enumeration {

  type EventType = Value

  val gameEvent, playerEvent, tileEvent = Value
}
