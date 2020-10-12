package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.events.PlayerEvent

trait Player extends Defined[PlayerDefinition] with History[PlayerEvent] {

  /** Compares two players. */
  def ==(obj: Player): Boolean = definition == obj.definition && history == obj.history

  override def equals(obj: Any): Boolean = obj match {
    case x: Player => x == this
    case _ => false
  }

  override def hashCode(): Int = 17 * definition.hashCode + 23

  override def toString: String =
    this.getClass.getSimpleName + ":" +
      definition.name
}

object Player {

  private class PlayerDefImpl(val definition: PlayerDefinition) extends Player {
    val history: Seq[PlayerEvent] = List()
  }

  case class PlayerImpl(definition: PlayerDefinition, history: Seq[PlayerEvent] = Seq()) extends Player

  /** Factory method that creates a new tile from the definition. */
  def apply(playerDefinition: PlayerDefinition): Player = new PlayerDefImpl(playerDefinition)

}
