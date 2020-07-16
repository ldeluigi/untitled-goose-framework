package model.entities.board

import model.Tile

trait Position {
  def tile: Tile
}

object Position {

  private class PositionImpl(val tile: Tile) extends Position {

  }

  def apply(tile: Tile): Position = new PositionImpl(tile)
}
