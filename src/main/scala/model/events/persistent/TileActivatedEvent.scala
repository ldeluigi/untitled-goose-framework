package model.events.persistent

import model.entities.runtime.Tile
import model.events.TileEvent

case class TileActivatedEvent(tile: Tile, turn: Int, cycle: Int)
  extends PersistentGameEvent with TileEvent
