package model.entities.board

import model.Tile

trait Board {
  def tiles: Set[TileDefinition]

  def name: String

  def disposition: Disposition

  def next(tile: TileDefinition): Option[TileDefinition]

}

object Board {

  private class BoardImpl() extends Board {
    private val myTiles: List[TileDefinition] = List.range(0, 10).map(i => TileDefinition(i))

    override def name: String = "MockBoard"

    override def disposition: Disposition = ???

    override def next(tile: TileDefinition): Option[TileDefinition] =
      tile.number flatMap (i => myTiles lift (i + 1)) orElse tile.next

    override def tiles: Set[TileDefinition] = myTiles.toSet
  }

  def apply(): Board = new BoardImpl() //TODO change
}
