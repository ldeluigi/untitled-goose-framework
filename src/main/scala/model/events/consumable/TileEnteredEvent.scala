package model.events.consumable

import model.events.{PlayerEvent, TileEvent}
import model.entities.runtime.{Player, Tile}

case class TileEnteredEvent(player: Player, tile: Tile, turn: Int, cycle: Int)
  extends ConsumableGameEvent with TileEvent with PlayerEvent
