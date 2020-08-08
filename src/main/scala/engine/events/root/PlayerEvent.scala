package engine.events.root

import model.Player

abstract class PlayerEvent(player: Player, currentTurn: Long, consumeTimes: Int = 1) extends AbstractGameEvent(currentTurn, consumeTimes) {

  def sourcePlayer: Player = player

  override def toString: String = super.toString + " Source: " + sourcePlayer.toString
}

object PlayerEvent {

  def unapply(arg: PlayerEvent): Option[Player] = Some(arg.sourcePlayer)

}


