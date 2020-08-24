package model

import engine.events.root.PlayerEvent

/** Models the concept of a player. */
trait Player {

  /** Player's name.
   *
   * @return the player's name
   */
  def name: String

  /** Player's history.
   *
   * @return a list of player triggered events.
   */
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

  /** Player's factory.
   *
   * @param name the player's name
   * @return thw newly created Player object
   */
  def apply(name: String): Player = new PlayerImpl(name)
}
