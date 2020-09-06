package untitled.goose.framework.model.events.consumable

import untitled.goose.framework.model.entities.runtime.{Player, Tile}
import untitled.goose.framework.model.events.{PlayerEvent, TileEvent}

case class PlayerPassedEvent(player: Player, playerPassing: Player, tile: Tile, turn: Int, cycle: Int)
  extends ConsumableGameEvent with TileEvent with PlayerEvent {

}
