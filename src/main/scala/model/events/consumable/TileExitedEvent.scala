package model.events.consumable

import model.events.{PlayerEvent, TileEvent}
import model.game.{Player, Tile}

case class TileExitedEvent(player: Player, tile: Tile, turn: Int, cycle: Int)
  extends ConsumableGameEvent with TileEvent with PlayerEvent
