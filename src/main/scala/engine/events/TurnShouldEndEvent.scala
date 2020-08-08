package engine.events

import engine.events.root.AbstractGameEvent

case class TurnShouldEndEvent(currentTurn: Long) extends AbstractGameEvent(currentTurn, 1) {

}
