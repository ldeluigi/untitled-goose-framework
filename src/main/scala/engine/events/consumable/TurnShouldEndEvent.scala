package engine.events.consumable

case class TurnShouldEndEvent(turn: Int, cycle: Int)
  extends ConsumableGameEvent
