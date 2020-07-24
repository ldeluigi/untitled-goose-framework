package engine.events.root

import model.Player

abstract class PlayerEvent(player: Player) extends GameEvent {

  override def group: List[String] = List()

  def source: Player = player
}

object PlayerEvent{

  def unapply(arg: PlayerEvent): Option[Player] = Some(arg.source)

}


