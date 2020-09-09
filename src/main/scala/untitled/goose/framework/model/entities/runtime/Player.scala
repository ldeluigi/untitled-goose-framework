package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.events.PlayerEvent

// TODO create immutable wrapper

/** A player currently playing the game. */
trait Player {

  /** The player's name. */
  def name: String

  /** The player's event history. */
  def history: Seq[PlayerEvent]

  /** The player's event history setter. */
  def history_=(history: Seq[PlayerEvent]): Unit

  /** Compares two players. */
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

  /** Factory method to create a new player. */
  def apply(name: String): Player = new PlayerImpl(name)
}