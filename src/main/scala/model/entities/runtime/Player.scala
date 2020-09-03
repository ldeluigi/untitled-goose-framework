package model.entities.runtime

import model.events.PlayerEvent

// TODO create immutable wrapper

trait Player {

  def name: String

  def history: Seq[PlayerEvent]

  def history_=(history: Seq[PlayerEvent]): Unit

  def ==(obj: Player): Boolean = name == obj.name

  override def equals(obj: Any): Boolean = obj match {
    case obj: Player => obj == this
    case _ => false
  }

  override def toString: String = this.getClass.getSimpleName + ": " + name

  override def hashCode(): Int = name.hashCode + 1
}

object Player {

  private class PlayerImpl(playerName: String) extends Player {

    override def name: String = playerName

    var history: Seq[PlayerEvent] = List()

  }

  /** Player's factory.
   *
   * @param name the player's name
   * @return the newly created Player object
   */
  def apply(name: String): Player = new PlayerImpl(name)
}
