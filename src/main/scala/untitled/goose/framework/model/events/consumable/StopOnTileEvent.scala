package untitled.goose.framework.model.events.consumable

import untitled.goose.framework.model.entities.definitions.TileDefinition
import untitled.goose.framework.model.entities.runtime.PlayerDefinition
import untitled.goose.framework.model.events.{PlayerEvent, TileEvent}

case class StopOnTileEvent(player: PlayerDefinition, tile: TileDefinition, turn: Int, cycle: Int)
  extends ConsumableGameEvent with PlayerEvent with TileEvent
