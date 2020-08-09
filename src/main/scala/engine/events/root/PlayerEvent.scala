package engine.events.root

import model.Player

abstract class PlayerEvent(player: Player, currentTurn: Long, consumeTimes: Int = 1) extends AbstractGameEvent(currentTurn, consumeTimes) {

  def source: Player = player

  override def toString: String = super.toString + " Source: " + source.toString
}

object PlayerEvent {

  def unapply(arg: PlayerEvent): Option[Player] = Some(arg.source)

}


