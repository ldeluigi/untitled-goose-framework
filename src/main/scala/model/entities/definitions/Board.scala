package model.entities.definitions

trait Board {

  def tiles: Set[TileDefinition]

  def name: String

  def disposition: Disposition

  /** Returns the next tile.
   *
   * @param tile the tile of which to return the next one
   * @return the next tile, if present
   */
  def next(tile: TileDefinition): Option[TileDefinition]

  /** Returns the previous tile.
   *
   * @param tile the tile of which to return the previous one
   * @return the previous tile, if present
   */
  def prev(tile: TileDefinition): Option[TileDefinition]

  /** Returns the board's first tile.
   *
   * @return the first tile.
   */
  def first: TileDefinition

  def ==(obj: Board): Boolean = tiles == obj.tiles && name == obj.name && disposition == obj.disposition

  override def equals(obj: Any): Boolean = obj match {
    case x: Board => x == this
    case _ => false
  }

  override def toString: String = this.getClass.getSimpleName + " " + name +
    " (first: " + first + ", disposition: " + disposition + ", tiles: " + tiles + ")"
}

object Board {

  private class GeneratedBoard(tileNum: Int, val disposition: Disposition) extends Board {

    private val myTiles: Seq[TileDefinition] = (1 to tileNum).map(i => TileDefinition(i))

    override def name: String = "GeneratedBoard"

    override def next(tile: TileDefinition): Option[TileDefinition] =
      tile.number flatMap (i => myTiles lift i)

    override def prev(tile: TileDefinition): Option[TileDefinition] =
      tile.number flatMap (i => myTiles lift i - 2)

    override def tiles: Set[TileDefinition] = myTiles.toSet

    override def first: TileDefinition = myTiles.head
  }

  private class BoardImpl(val name: String, val tiles: Set[TileDefinition], val disposition: Disposition) extends Board {

    override def next(tile: TileDefinition): Option[TileDefinition] =
      tile.number flatMap (i => tiles.toSeq.sorted lift i)

    override def prev(tile: TileDefinition): Option[TileDefinition] =
      tile.number flatMap (i => tiles.toSeq.sorted lift i - 2)

    override def first: TileDefinition = tiles.toSeq.min
  }

  def apply(tileNum: Int, disposition: Disposition): Board = new GeneratedBoard(tileNum, disposition)

  def apply(name: String, tiles: Set[TileDefinition], disposition: Disposition): Board = new BoardImpl(name, tiles, disposition)

  def create(): BoardBuilderImpl = BoardBuilderImpl()

}
