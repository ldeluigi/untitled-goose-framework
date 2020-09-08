package untitled.goose.framework.model.events.consumable

import untitled.goose.framework.model.entities.runtime.{Player, Tile}
import untitled.goose.framework.model.events.{PlayerEvent, TileEvent}

case class TeleportEvent(tile: Tile, player: Player, turn: Int, cycle: Int)
  extends ConsumableGameEvent with PlayerEvent with TileEvent
