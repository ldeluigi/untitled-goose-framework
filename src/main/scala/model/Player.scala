package model

import engine.events.PlayerEvent

trait Player {
  def name: String

  def history: List[PlayerEvent]

  def history_=(history: List[PlayerEvent]): Unit

  // TODO controllare se inserire un metodo per modificare la history aggiungendo eventi
}

object Player {

  private class PlayerImpl(playerName: String) extends Player {

    override def name: String = playerName

    var history: List[PlayerEvent] = List()

    override def equals(obj: Any): Boolean = obj match {
      case obj: Player => this.name == obj.name
    }

    override def toString: String = "Player: " + name
  }

  def apply(name: String): Player = new PlayerImpl(name)
}
