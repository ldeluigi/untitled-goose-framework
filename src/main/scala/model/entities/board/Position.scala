package model.entities.board

import model.Tile

trait Position {
  def tile: Tile
}

object Position {
  def apply(): Position = PositionImpl()
}

case class PositionImpl() extends Position {
  override def tile: Tile = ???
}
