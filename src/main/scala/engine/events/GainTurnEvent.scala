package engine.events

import engine.events.root.{ConsumableGameEvent, PlayerEvent}
import model.Player

case class GainTurnEvent(source: Player, currentTurn: Int)
  extends ConsumableGameEvent(currentTurn) with PlayerEvent
