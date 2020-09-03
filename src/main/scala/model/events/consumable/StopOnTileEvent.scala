package model.events.consumable

import model.events.{PlayerEvent, TileEvent}
import model.entities.runtime.{Player, Tile}

case class StopOnTileEvent(player: Player, tile: Tile, turn: Int, cycle: Int)
  extends ConsumableGameEvent with PlayerEvent with TileEvent
