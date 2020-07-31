package engine.events

import engine.events.root.AbstractGameEvent

case class TurnShouldEndEvent(currentTurn: Long) extends AbstractGameEvent(currentTurn) {
  override def name: String = "TurnShouldEnd"

  override def isConsumable: Boolean = true
}
