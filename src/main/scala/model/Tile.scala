package model

import engine.events.TileEvent
import model.entities.board.TileDefinition

trait Tile extends TileDefinition {
  def history: Seq[TileEvent]

  def history_=(history: Seq[TileEvent]): Unit

}

object Tile {

  private class TileImpl(tile: TileDefinition) extends Tile {

    var history: Seq[TileEvent] = List()

    override def number: Option[Int] = tile.number

    override def name: Option[String] = tile.name

    override def groups: Set[String] = tile.groups

    override def equals(obj: Any): Boolean = {
      // TODO history comparison???
      obj match {
        case t: Tile =>
          (number.isDefined && t.number.isDefined && number.get == t.number.get) ||
            (name.isDefined && t.name.isDefined && name.get == t.name.get)
      }
    }

    override def toString: String =
      "Tile: " +
        (if (number.isDefined) " number " + number.get else "") +
        (if (name.isDefined) name.get else "")
  }

  def apply(tileDefinition: TileDefinition): Tile = new TileImpl(tileDefinition)
}
