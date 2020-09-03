package model.events.persistent

import model.events.TileEvent
import model.game.Tile

case class TileActivatedEvent(tile: Tile, turn: Int, cycle: Int)
  extends PersistentGameEvent with TileEvent
