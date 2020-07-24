package model

import engine.events.root.PlayerEvent

trait Player {
  def name: String

  def history: List[PlayerEvent]

  def history_=(history: List[PlayerEvent]): Unit
}

object Player {

  private class PlayerImpl(playerName: String) extends Player {

    override def name: String = playerName

    var history: List[PlayerEvent] = List()

    override def equals(obj: Any): Boolean = obj match {
      case obj: Player => this.name == obj.name
    }
  }

  def apply(name: String): Player = new PlayerImpl(name)
}
