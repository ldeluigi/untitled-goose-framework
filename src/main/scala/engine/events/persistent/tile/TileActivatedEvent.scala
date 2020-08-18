package engine.events.persistent.tile

import engine.events.TileEvent
import engine.events.persistent.PersistentGameEvent
import model.Tile

case class TileActivatedEvent(tile: Tile, turn: Int, cycle: Int)
  extends PersistentGameEvent with TileEvent
