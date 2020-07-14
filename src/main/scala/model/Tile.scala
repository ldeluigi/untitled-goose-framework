package model

import model.entities.board.TileDefinition

trait Tile extends TileDefinition {
  def history: List[GameEventHandler]
}

object Tile{
  def apply() = MockTile()
}

case class MockTile() extends Tile{
  override def history: List[GameEventHandler] = ???
}
