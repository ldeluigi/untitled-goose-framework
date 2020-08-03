package engine.events

import engine.events.root.TileEvent
import model.Tile

case class TileExitedEvent(tile: Tile, currentTurn: Long) extends TileEvent(tile, currentTurn) {
  override def name: String = "Tile Exited"

  override def isConsumable: Boolean = true
}
