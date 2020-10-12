package untitled.goose.framework.model.events.persistent

import untitled.goose.framework.model.entities.definitions.TileDefinition
import untitled.goose.framework.model.events.TileEvent

case class TileActivatedEvent(tile: TileDefinition, turn: Int, cycle: Int)
  extends PersistentGameEvent with TileEvent
