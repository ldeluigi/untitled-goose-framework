package untitled.goose.framework.model.events

import untitled.goose.framework.model.entities.runtime.PlayerDefinition

/** An event that regards a player. */
trait PlayerEvent extends GameEvent {

  /** The player that this event regarded. */
  def player: PlayerDefinition

  override def toString: String = super.toString + " " + player.toString
}

object PlayerEvent {

  /** Unapply method to extract the player of a PlayerEvent. */
  def unapply(arg: PlayerEvent): Option[PlayerDefinition] = Some(arg.player)

}


