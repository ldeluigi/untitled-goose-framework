package model.entities.board

trait Board {

  def tiles: Set[TileDefinition]

  def name: String

  def disposition: Disposition

  def next(tile: TileDefinition): Option[TileDefinition]

  def first: TileDefinition

}

object Board {

  private class BoardImpl(tileNum: Int, val disposition: Disposition) extends Board {
    private val myTiles: List[TileDefinition] = (1 to tileNum).toList.map(i => TileDefinition(i))

    override def name: String = "MockBoard" // TODO what?

    override def next(tile: TileDefinition): Option[TileDefinition] =
      tile.number flatMap (i => myTiles lift (i + 1))

    override def tiles: Set[TileDefinition] = myTiles.toSet

    override def first: TileDefinition = myTiles.head
  }

  def apply(tileNum: Int, disposition: Disposition): Board = new BoardImpl(tileNum, disposition) //TODO change
}
