package untitled.goose.framework.model.events.consumable

import untitled.goose.framework.model.entities.runtime.Player
import untitled.goose.framework.model.events.PlayerEvent

case class InvertMovementEvent(player: Player, turn: Int, cycle: Int)
  extends ConsumableGameEvent with PlayerEvent {

}
