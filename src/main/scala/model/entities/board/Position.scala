package model.entities.board

import model.Tile

/**
 * Defines the position of a tile.
 */
trait Position {
  def tile: Tile
}

object Position {

  /**
   * @param tile the tile to base the position on.
   */
  private class PositionImpl(val tile: Tile) extends Position {

    override def equals(obj: Any): Boolean = {
      obj match {
        case obj: Position => this.tile.equals(obj.tile)
        case _ => false
      }
    }
  }

  /** A factory that creare a new position object based on a tile. */
  def apply(tile: Tile): Position = new PositionImpl(tile)
}
