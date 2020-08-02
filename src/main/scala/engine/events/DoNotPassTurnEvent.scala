package engine.events

import engine.events.root.AbstractGameEvent

case class DoNotPassTurnEvent(currentTurn: Long) extends AbstractGameEvent(currentTurn) {
  override def name: String = "DoNotPassTurnEvent"

  override def isConsumable: Boolean = true
}
