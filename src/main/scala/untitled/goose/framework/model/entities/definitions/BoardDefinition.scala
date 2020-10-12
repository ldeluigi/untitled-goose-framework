package untitled.goose.framework.model.entities.definitions

/** The definition of the definition of a game. */
trait BoardDefinition {

  /** The definition of every tile that belongs to this definition. */
  def tiles: Set[TileDefinition]

  /** The name of the definition game. */
  def name: String

  /** The disposition function for the tiles. */
  def disposition: Disposition

  def tileOrdering: OneWayPath[TileDefinition]

  /** Compares two boards. */
  def ==(obj: BoardDefinition): Boolean =
    tiles == obj.tiles &&
      name == obj.name &&
      disposition == obj.disposition &&
      tileOrdering == obj.tileOrdering

  override def equals(obj: Any): Boolean = obj match {
    case x: BoardDefinition => x == this
    case _ => false
  }

  override def toString: String =
    this.getClass.getSimpleName + " " + name +
      " (disposition: " + disposition + ", ordering: " + tileOrdering + ", tiles: " + tiles + ")"
}

object BoardDefinition {

  private class GeneratedBoardDefinition(tileNum: Int,
                                         val disposition: Disposition,
                                         val name: String) extends BoardDefinition {

    private val myTiles: Seq[TileDefinition] = (1 to tileNum).map(i => TileDefinition(i))

    override def tiles: Set[TileDefinition] = myTiles.toSet

    override def tileOrdering: OneWayPath[TileDefinition] =
      OneWayPath(myTiles.head,
        _.number.flatMap(i => myTiles.lift(i)),
        _.number.flatMap(i => myTiles.lift(i - 2))
      )
  }

  private class BoardDefinitionImpl(val name: String,
                                    val tiles: Set[TileDefinition],
                                    val disposition: Disposition,
                                    val first: TileDefinition) extends BoardDefinition {
    private val _sorted = tiles.toSeq.sorted

    override def tileOrdering: OneWayPath[TileDefinition] =
      OneWayPath(first,
        _.number.flatMap(i => _sorted.lift(i)),
        _.number.flatMap(i => _sorted.lift(i - 2))
      )
  }


  /**
   * Factory method that generates a definition of given tiles, from 1 to N.
   *
   * @param name        The name of the definition game.
   * @param tileNum     The number of tiles to generate.
   * @param disposition The disposition of the tiles.
   * @return A new definition.
   */
  def apply(name: String, tileNum: Int, disposition: Disposition): BoardDefinition =
    new GeneratedBoardDefinition(tileNum, disposition, name)

  /**
   * Factory method that creates a definition with given parameters.
   *
   * @param name        The name of the definition game.
   * @param tiles       The tile definitions of the definition.
   * @param disposition The disposition of the tiles.
   * @param start       The starting tile.
   * @return A new definition.
   */
  def apply(name: String,
            tiles: Set[TileDefinition],
            disposition: Disposition,
            start: TileDefinition): BoardDefinition =
    new BoardDefinitionImpl(name, tiles, disposition, start)

  /**
   * Creates a definition builder for a fluent syntax.
   *
   * @return A new definition builder.
   */
  def create(): BoardBuilder = BoardBuilder()
}
