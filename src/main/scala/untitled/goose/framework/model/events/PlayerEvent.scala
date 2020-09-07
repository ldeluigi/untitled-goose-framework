package untitled.goose.framework.model.events

import untitled.goose.framework.model.entities.runtime.Player

/** An event that regards a player. */
trait PlayerEvent extends GameEvent {

  /** The player that this event regarded. */
  def player: Player

  override def toString: String = super.toString + " " + player.toString
}

object PlayerEvent {

  /** Unapply method to extract the player of a PlayerEvent. */
  def unapply(arg: PlayerEvent): Option[Player] = Some(arg.player)

}


