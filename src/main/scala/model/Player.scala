package model

import engine.events.root.GameEvent

trait Player {
  def name: String

  def history: List[GameEvent] //TODO change
}

object Player {

  private class PlayerImpl(playerName: String) extends Player {

    override def name: String = playerName

    override def history: List[GameEvent] = ???

    override def equals(obj: Any): Boolean = obj match {
      case obj: Player => this.name == obj.name
    }
  }

  def apply(name: String): Player = new PlayerImpl(name)
}
