package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.entities.definitions.TileDefinition
import untitled.goose.framework.model.events.TileEvent

trait Tile extends Defined[TileDefinition] {
  def history: Seq[TileEvent]

  def history_=(history: Seq[TileEvent]): Unit

  def ==(obj: Tile): Boolean = definition == obj.definition

  override def equals(obj: Any): Boolean = obj match {
    case x: Tile => x == this
    case _ => false
  }

  override def hashCode(): Int = definition.hashCode + 1

  override def toString: String =
    this.getClass.getSimpleName + ":" +
      definition.number.map(" number " + _).getOrElse("") +
      definition.name.map(" name " + _).getOrElse("")
}

object Tile {

  private class TileImpl(val definition: TileDefinition) extends Tile {

    var history: Seq[TileEvent] = List()
  }

  def apply(tileDefinition: TileDefinition): Tile = new TileImpl(tileDefinition)

  implicit def compare[A <: Tile]: Ordering[A] = Ordering.by(_.definition)
}
