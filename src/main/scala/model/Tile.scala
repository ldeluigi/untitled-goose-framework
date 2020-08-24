package model

import engine.events.root.TileEvent
import model.entities.board.TileDefinition

/** Models the concept of a tile. */
trait Tile extends TileDefinition {

  /** Tile's history.
   *
   * @return the list of tile's event
   */
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

  /** A tile's factory.
   *
   * @param tileDefinition the TileDefinition input object
   * @return the newly created tile
   */
  def apply(tileDefinition: TileDefinition): Tile = new TileImpl(tileDefinition)
}
