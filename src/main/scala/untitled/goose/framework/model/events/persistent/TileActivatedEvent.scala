package untitled.goose.framework.model.events.persistent

import untitled.goose.framework.model.entities.runtime.Tile
import untitled.goose.framework.model.events.TileEvent

case class TileActivatedEvent(tile: Tile, turn: Int, cycle: Int)
  extends PersistentGameEvent with TileEvent
