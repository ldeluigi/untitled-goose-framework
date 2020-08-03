package engine.events

import engine.events.root.TileEvent
import model.{Player, Tile}


case class StopOnTileEvent(player: Player, tile: Tile, currentTurn: Long) extends TileEvent(tile, currentTurn) {
  override def name: String = "Stop On Tile"
}
