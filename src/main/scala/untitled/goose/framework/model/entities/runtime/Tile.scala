package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.entities.definitions.TileDefinition
import untitled.goose.framework.model.events.TileEvent

/** A tile on the board, at runtime. */
trait Tile extends Defined[TileDefinition] {

  /** The history of events related to this tile. */
  def history: Seq[TileEvent]

  /** The tile's event history setter. */
  def history_=(history: Seq[TileEvent]): Unit

  /** Compares two tiles. */
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

  /** Factory method that creates a new tile from the definition. */
  def apply(tileDefinition: TileDefinition): Tile = new TileImpl(tileDefinition)

  /** Default comparator, based on the definition's default comparator. */
  implicit def compare[A <: Tile]: Ordering[A] = Ordering.by(_.definition)
}
