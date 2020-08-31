package model

import model.events.PlayerEvent

trait Player {

  def name: String

  def history: Seq[PlayerEvent]

  def history_=(history: Seq[PlayerEvent]): Unit
}

object Player {

  private class PlayerImpl(playerName: String) extends Player {

    override def name: String = playerName

    var history: Seq[PlayerEvent] = List()

    override def equals(obj: Any): Boolean = obj match {
      case obj: Player => this.name == obj.name
    }

    override def toString: String = "Player: " + name
  }

  /** Player's factory.
   *
   * @param name the player's name
   * @return the newly created Player object
   */
  def apply(name: String): Player = new PlayerImpl(name)
}
