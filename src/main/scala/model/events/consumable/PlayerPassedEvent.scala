package model.events.consumable

import model.entities.runtime.{Player, Tile}
import model.events.{PlayerEvent, TileEvent}

case class PlayerPassedEvent(player: Player, playerPassing: Player, tile: Tile, turn: Int, cycle: Int)
  extends ConsumableGameEvent with TileEvent with PlayerEvent {

}
