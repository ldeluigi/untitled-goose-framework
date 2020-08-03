package engine.events

import engine.events.root.PlayerEvent
import model.{Player, Tile}

case class TeleportEvent(teleportTo: Tile, player: Player, currentTurn: Long) extends PlayerEvent(player, currentTurn){

  def tile: Tile = teleportTo

  override def name: String = "Teleport Event"

  override def isConsumable: Boolean = true
}
