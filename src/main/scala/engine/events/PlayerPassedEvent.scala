package engine.events

import engine.events.root.{ConsumableGameEvent, PlayerEvent}
import model.{Player, Tile}

case class PlayerPassedEvent(source: Player, passingPlayer: Player, tile: Tile, currentTurn: Int)
  extends ConsumableGameEvent(currentTurn) with PlayerEvent {

  override def toString: String = super.toString + " PlayerPassing: " + passingPlayer.toString + " on tile: " + tile.toString
}
