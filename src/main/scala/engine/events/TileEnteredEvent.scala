package engine.events

import engine.events.root.TileEvent
import model.Tile

case class TileEnteredEvent(tile: Tile, currentTurn: Long) extends TileEvent(tile, currentTurn) {
  override def name: String = "Tile Entered"

  override def isConsumable: Boolean = true
}

