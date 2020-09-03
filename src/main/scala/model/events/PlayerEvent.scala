package model.events

import model.entities.runtime.Player

trait PlayerEvent extends GameEvent {

  def player: Player

  override def toString: String = super.toString + " " + player.toString
}

object PlayerEvent {

  def unapply(arg: PlayerEvent): Option[Player] = Some(arg.player)

}

