package engine.events.root

import model.Player

trait PlayerEvent extends GameEvent {

  def source: Player

  override def toString: String = super.toString + " Source: " + source.toString
}

object PlayerEvent {

  def unapply(arg: PlayerEvent): Option[Player] = Some(arg.source)

}


