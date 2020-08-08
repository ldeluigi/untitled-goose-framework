package engine.events

import engine.events.root.TileEvent
import model.{Player, Tile}

case class TileExitedEvent(player: Player, tile: Tile, currentTurn: Long) extends TileEvent(tile, currentTurn) {

}
