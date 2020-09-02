package model

import model.entities.board.TileDefinition
import model.events.TileEvent

trait Tile {
  def history: Seq[TileEvent]

  def history_=(history: Seq[TileEvent]): Unit

  def definition: TileDefinition

  def ==(obj: Tile): Boolean = history == obj.history && definition == obj.definition

  override def equals(obj: Any): Boolean = obj match {
    case x: Tile => x == this
    case _ => false
  }
}

object Tile {

  private class TileImpl(val definition: TileDefinition) extends Tile {

    var history: Seq[TileEvent] = List()

    override def toString: String =
      "Tile: " +
        (if (definition.number.isDefined) " number " + definition.number.get else "") +
        (if (definition.name.isDefined) definition.name.get else "")
  }

  def apply(tileDefinition: TileDefinition): Tile = new TileImpl(tileDefinition)

  implicit def compare[A <: Tile]: Ordering[A] = (x: A, y: A) => TileDefinition.compare.compare(x.definition, y.definition)
}
