package model.entities.board

import model.Tile

trait Position {
  def tile: Tile
}

object Position{
  def apply() = MockPosition()
}

case class MockPosition() extends Position{
  override def tile: Tile = Tile
}
