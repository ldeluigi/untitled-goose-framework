package untitled.goose.framework.model.entities.runtime

/** A player currently playing the game. */
trait PlayerDefinition {

  /** The player's name. */
  def name: String

  /** Compares two players. */
  def ==(obj: PlayerDefinition): Boolean = name == obj.name

  override def equals(obj: Any): Boolean = obj match {
    case obj: PlayerDefinition => obj == this
    case _ => false
  }

  override def toString: String = this.getClass.getSimpleName + ": " + name

  override def hashCode(): Int = name.hashCode + 1
}

object PlayerDefinition {

  case class PlayerDefinitionImpl(name: String) extends PlayerDefinition

}
