package engine.events

import engine.events.root.TileEvent
import model.Tile


case class StopOnTileEvent(tile: Tile, currentTurn: Long) extends TileEvent(tile, currentTurn) {
  override def name: String = "Stop On Tile"

  override def isConsumable: Boolean = true
}
