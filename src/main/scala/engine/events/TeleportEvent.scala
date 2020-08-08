package engine.events

import engine.events.root.PlayerEvent
import model.{Player, Tile}

case class TeleportEvent(teleportTo: Tile, player: Player, currentTurn: Long) extends PlayerEvent(player, currentTurn) {

  def tile: Tile = teleportTo

  override def toString: String = super.toString + " teleport to: " + teleportTo.toString

}
