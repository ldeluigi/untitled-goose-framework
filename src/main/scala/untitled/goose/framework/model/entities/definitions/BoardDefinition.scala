package untitled.goose.framework.model.entities.definitions

/** The definition of the definition of a game. */
trait BoardDefinition {

  /** The definition of every tile that belongs to this definition. */
  def tiles: Set[TileDefinition]

  /** The name of the definition game. */
  def name: String

  /** The disposition function for the tiles. */
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

  /** Returns the definition's first tile.
   *
   * @return the first tile.
   */
  def first: TileDefinition

  /** Compares two boards. */
  def ==(obj: BoardDefinition): Boolean = tiles == obj.tiles && name == obj.name && disposition == obj.disposition

  override def equals(obj: Any): Boolean = obj match {
    case x: BoardDefinition => x == this
    case _ => false
  }

  override def toString: String = this.getClass.getSimpleName + " " + name +
    " (first: " + first + ", disposition: " + disposition + ", tiles: " + tiles + ")"
}

object BoardDefinition {

  private class GeneratedBoardDefinition(tileNum: Int, val disposition: Disposition, val name: String) extends BoardDefinition {

    private val myTiles: Seq[TileDefinition] = (1 to tileNum).map(i => TileDefinition(i))

    override def next(tile: TileDefinition): Option[TileDefinition] =
      tile.number flatMap (i => myTiles lift i)

    override def prev(tile: TileDefinition): Option[TileDefinition] =
      tile.number flatMap (i => myTiles lift i - 2)

    override def tiles: Set[TileDefinition] = myTiles.toSet

    override def first: TileDefinition = myTiles.head
  }

  private class BoardDefinitionImpl(val name: String, val tiles: Set[TileDefinition], val disposition: Disposition) extends BoardDefinition {

    override def next(tile: TileDefinition): Option[TileDefinition] =
      tile.number flatMap (i => tiles.toSeq.sorted lift i)

    override def prev(tile: TileDefinition): Option[TileDefinition] =
      tile.number flatMap (i => tiles.toSeq.sorted lift i - 2)

    override def first: TileDefinition = tiles.toSeq.min
  }

  /**
   * Factory method that generates a definition of given tiles, from 1 to N.
   * @param name The name of the definition game.
   * @param tileNum The number of tiles to generate.
   * @param disposition The disposition of the tiles.
   * @return A new definition.
   */
  def apply(name: String, tileNum: Int, disposition: Disposition): BoardDefinition = new GeneratedBoardDefinition(tileNum, disposition, name)

  /**
   * Factory method that creates a definition with given parameters.
   * @param name The name of the definition game.
   * @param tiles The tile definitions of the definition.
   * @param disposition The disposition of the tiles.
   * @return A new definition.
   */
  def apply(name: String, tiles: Set[TileDefinition], disposition: Disposition): BoardDefinition = new BoardDefinitionImpl(name, tiles, disposition)

  /**
   * Creates a definition builder for a fluent syntax.
   * @return A new definition builder.
   */
  def create(): BoardBuilder = BoardBuilder()
}
