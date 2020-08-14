package engine.events

import engine.events.root.{ConsumableGameEvent, TileEvent}
import model.{Player, Tile}

case class PlayerPassedEvent(playerPassed: Player, playerPassing: Player, source: Tile, currentTurn: Int)
  extends ConsumableGameEvent(currentTurn) with TileEvent {

  override def toString: String = super.toString + " player " + playerPassing.toString + " passed " + playerPassed.toString
}
