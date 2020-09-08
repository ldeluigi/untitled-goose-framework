package untitled.goose.framework.model.events.consumable

import untitled.goose.framework.model.entities.runtime.{Player, Tile}
import untitled.goose.framework.model.events.{PlayerEvent, TileEvent}

case class TileEnteredEvent(player: Player, tile: Tile, turn: Int, cycle: Int)
  extends ConsumableGameEvent with TileEvent with PlayerEvent
