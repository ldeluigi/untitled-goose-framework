package engine.events

import engine.events.root.ConsumableGameEvent

case class TurnShouldEndEvent(currentTurn: Int)
  extends ConsumableGameEvent(currentTurn)
