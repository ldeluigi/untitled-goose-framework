package model

import engine.events.root.TileEvent
import model.entities.board.TileDefinition

trait Tile extends TileDefinition {
  def history: List[TileEvent]

  def history_=(history: List[TileEvent]): Unit

  // TODO controllare se inserire un metodo per modificare la history aggiungendo eventi
}

object Tile {

  private class TileImpl(tile: TileDefinition) extends Tile {

    var history: List[TileEvent] = List()

    override def number: Option[Int] = tile.number

    override def name: Option[String] = tile.name

    override def groups: Set[String] = tile.groups

    override def equals(obj: Any): Boolean = {
      // TODO rewrite with functional style please
      // TODO history comparison???
      obj match {
        case t: Tile =>
          if (number.isDefined && t.number.isDefined) {
            return number.get == t.number.get
          }

          if (name.isDefined && t.name.isDefined) {
            return name.get == t.name.get
          }

          false
      }
    }

    override def toString: String =
      "Tile: " +
        (if (number.isDefined) " number " + number.get else "") +
        (if (name.isDefined) name.get else "")
  }

  def apply(tileDefinition: TileDefinition): Tile = new TileImpl(tileDefinition)
}
