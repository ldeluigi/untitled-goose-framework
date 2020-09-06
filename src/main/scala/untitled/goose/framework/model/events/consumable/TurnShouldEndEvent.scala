package untitled.goose.framework.model.events.consumable

case class TurnShouldEndEvent(turn: Int, cycle: Int)
  extends ConsumableGameEvent
