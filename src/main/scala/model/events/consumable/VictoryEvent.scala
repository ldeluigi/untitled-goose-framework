package model.events.consumable

import model.Player
import model.events.PlayerEvent

case class VictoryEvent(player: Player, turn: Int, cycle: Int)
  extends ConsumableGameEvent with PlayerEvent
