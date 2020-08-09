package engine.events

import engine.events.root.{ConsumableGameEvent, PlayerEvent}
import model.{Player, Tile}

case class TeleportEvent(teleportTo: Tile, source: Player, currentTurn: Int)
  extends ConsumableGameEvent(currentTurn) with PlayerEvent {

  override def toString: String = super.toString + " teleport to: " + teleportTo.toString

}
