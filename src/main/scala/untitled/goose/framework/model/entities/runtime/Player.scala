package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.events.PlayerEvent

trait Player extends Defined[PlayerDefinition] with History[PlayerEvent] {

  /** Compares two players. */
  def ==(obj: Tile): Boolean = definition == obj.definition && history == obj.history

  override def equals(obj: Any): Boolean = obj match {
    case x: Tile => x == this
    case _ => false
  }

  override def hashCode(): Int = definition.hashCode + 1

  override def toString: String =
    this.getClass.getSimpleName + ":" +
      definition.name
}

object Player {

  private class PlayerDefImpl(val definition: PlayerDefinition) extends Player {
    val history: Seq[PlayerEvent] = List()
  }

  case class PlayerImpl(definition: PlayerDefinition, history: Seq[PlayerEvent]) extends Player

  /** Factory method that creates a new tile from the definition. */
  def apply(playerDefinition: PlayerDefinition): Player = new PlayerDefImpl(playerDefinition)

}
