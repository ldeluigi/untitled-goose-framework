package engine.events.root

import model.Player

abstract class PlayerEvent(player: Player, currentTurn: Long) extends AbstractGameEvent(currentTurn) {

  def sourcePlayer: Player = player
}

abstract class ConsumablePlayerEvent(player: Player, currentTurn: Long, consumeTimes: Int) extends ConsumableGameEvent(currentTurn, consumeTimes) {

  def sourcePlayer: Player = player

}

object PlayerEvent {
  def unapply(arg: PlayerEvent): Option[Player] = Some(arg.sourcePlayer)
}


