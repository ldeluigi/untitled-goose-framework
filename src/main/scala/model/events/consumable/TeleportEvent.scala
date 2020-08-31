package model.events.consumable

import model.events.{PlayerEvent, TileEvent}
import model.{Player, Tile}

case class TeleportEvent(tile: Tile, player: Player, turn: Int, cycle: Int)
  extends ConsumableGameEvent with PlayerEvent with TileEvent
