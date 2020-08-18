package engine.events.consumable

import engine.events.PlayerEvent
import model.Player

case class SkipTurnEvent(player: Player, turn: Int, cycle: Int)
  extends ConsumableGameEvent with PlayerEvent
