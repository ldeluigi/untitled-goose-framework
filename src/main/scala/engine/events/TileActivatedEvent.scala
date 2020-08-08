package engine.events

import engine.events.root.TileEvent
import model.Tile

case class TileActivatedEvent(tile: Tile, currentTurn: Long) extends TileEvent(tile, currentTurn) {
  override def isConsumable: Boolean = false
}

