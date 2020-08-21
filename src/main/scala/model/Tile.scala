package model

import engine.events.TileEvent
import model.entities.board.TileDefinition

trait Tile {
  def history: Seq[TileEvent]

  def history_=(history: Seq[TileEvent]): Unit

  def definition: TileDefinition
}

object Tile {

  private class TileImpl(val definition: TileDefinition) extends Tile {

    var history: Seq[TileEvent] = List()

    override def equals(obj: Any): Boolean = {
      obj match {
        case t: Tile => definition == t.definition && history == t.history
        case _ => false
      }
    }

    override def toString: String =
      "Tile: " +
        (if (definition.number.isDefined) " number " + definition.number.get else "") +
        (if (definition.name.isDefined) definition.name.get else "")
  }

  def apply(tileDefinition: TileDefinition): Tile = new TileImpl(tileDefinition)

  implicit def compare[A <: Tile]: Ordering[A] = (x: A, y: A) => TileDefinition.compare.compare(x.definition, y.definition)
}
