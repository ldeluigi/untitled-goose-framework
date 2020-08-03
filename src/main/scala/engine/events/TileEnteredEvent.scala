package engine.events

import engine.events.root.TileEvent
import model.{Player, Tile}

case class TileEnteredEvent(player: Player, tile: Tile, currentTurn: Long) extends TileEvent(tile, currentTurn) {
  override def name: String = "Tile Entered"

  override def isConsumable: Boolean = true
}

