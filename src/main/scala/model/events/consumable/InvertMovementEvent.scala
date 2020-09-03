package model.events.consumable

import model.events.PlayerEvent
import model.entities.runtime.Player

case class InvertMovementEvent(player: Player, turn: Int, cycle: Int)
  extends ConsumableGameEvent with PlayerEvent {

}
