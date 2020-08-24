package model.entities.board

import model.Tile

trait Position {
  def tile: Tile
}

object Position {

  private class PositionImpl(val tile: Tile) extends Position {

    override def equals(obj: Any): Boolean = {
      obj match {
        case obj: Position => this.tile.equals(obj.tile)
        case _ => false
      }
    }
  }

  def apply(tile: Tile): Position = new PositionImpl(tile)
}
