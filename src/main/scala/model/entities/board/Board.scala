package model.entities.board

import model.Tile

trait Board {
  def tiles: Set[TileDefinition]

  def name: String

  def disposition: Disposition

  def next(tile: TileDefinition): Option[TileDefinition]

}

object Board {

  private class BoardImpl(tileNum: Int) extends Board {
    private val myTiles: List[TileDefinition] = List.range(0, tileNum).map(i => TileDefinition(i))

    override def name: String = "MockBoard"

    override def disposition: Disposition = ???

    override def next(tile: TileDefinition): Option[TileDefinition] =
      tile.number flatMap (i => myTiles lift (i + 1))

    override def tiles: Set[TileDefinition] = myTiles.toSet
  }

  def apply(tileNum: Int): Board = new BoardImpl(tileNum) //TODO change
}
