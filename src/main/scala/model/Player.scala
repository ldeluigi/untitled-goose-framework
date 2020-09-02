package model

import model.events.PlayerEvent

trait Player {

  def name: String

  def history: Seq[PlayerEvent]

  def history_=(history: Seq[PlayerEvent]): Unit

  def ==(obj: Player): Boolean = name == obj.name && history == obj.history

  override def equals(obj: Any): Boolean = obj match {
    case obj: Player => obj == this
    case _ => false
  }
}

object Player {

  private class PlayerImpl(playerName: String) extends Player {

    override def name: String = playerName

    var history: Seq[PlayerEvent] = List()

    override def toString: String = "Player: " + name
  }

  /** Player's factory.
   *
   * @param name the player's name
   * @return the newly created Player object
   */
  def apply(name: String): Player = new PlayerImpl(name)
}
