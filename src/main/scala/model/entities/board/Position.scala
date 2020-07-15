package model.entities.board

import model.Tile

trait Position {
  def tile: Tile
}

object Position {
  def apply(tile: Tile): Position = PositionImpl(tile)
}

case class PositionImpl(tile: Tile) extends Position {

}
