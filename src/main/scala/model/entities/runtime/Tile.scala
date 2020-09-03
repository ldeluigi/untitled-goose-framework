package model.entities.runtime

import model.entities.definitions.TileDefinition
import model.events.TileEvent

// TODO create immutable wrapper

trait Tile {
  def history: Seq[TileEvent]

  def history_=(history: Seq[TileEvent]): Unit

  def definition: TileDefinition

  def ==(obj: Tile): Boolean = history == obj.history && definition == obj.definition

  override def equals(obj: Any): Boolean = obj match {
    case x: Tile => x == this
    case _ => false
  }

  override def hashCode(): Int = 7 * history.hashCode + 11 * definition.hashCode

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

  implicit def compare[A <: Tile]: Ordering[A] = (x: A, y: A) => TileDefinition.compare.compare(x.definition, y.definition)
}
