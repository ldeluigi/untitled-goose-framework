package model.events.consumable

import model.entities.runtime.{Player, Tile}
import model.events.{PlayerEvent, TileEvent}

case class StopOnTileEvent(player: Player, tile: Tile, turn: Int, cycle: Int)
  extends ConsumableGameEvent with PlayerEvent with TileEvent
