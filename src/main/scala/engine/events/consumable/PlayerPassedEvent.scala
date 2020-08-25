package engine.events.consumable

import engine.events.{PlayerEvent, TileEvent}
import model.{Player, Tile}

case class PlayerPassedEvent(player: Player, playerPassing: Player, tile: Tile, turn: Int, cycle: Int)
  extends ConsumableGameEvent with TileEvent with PlayerEvent {

}