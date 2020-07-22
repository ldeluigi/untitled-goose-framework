package engine.events

import model.{GameEvent, Player}

class PlayerEvent(groupList: List[String], player: Player) extends GameEvent {

  override def name: String = "PlayerEvent"

  override def group: List[String] = groupList

  def source: Player = player
}

object PlayerEvent{
  def apply(groupList: List[String], player: Player): PlayerEvent = new PlayerEvent(groupList, player)
}


