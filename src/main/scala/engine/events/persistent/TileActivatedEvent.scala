package engine.events.persistent

import engine.events.TileEvent
import model.Tile

case class TileActivatedEvent(tile: Tile, turn: Int, cycle: Int)
  extends PersistentGameEvent with TileEvent
