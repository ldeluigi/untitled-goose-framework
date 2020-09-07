package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.entities.definitions.{BoardDefinition, TileDefinition}

/** At runtime, a board state is defined by tiles state. */
trait Board extends Defined[BoardDefinition] {

  /** The set of tile in this board at runtime. */
  def tiles: Set[Tile]

  /** Helper method that computes the next tile,
   * if possible, given some input tile.
   */
  def next(tile: Tile): Option[Tile]

  /** Helper method that computes the previous tile,
   * if possible, given some input tile.
   */
  def prev(tile: Tile): Option[Tile]

  /** Returns the tile at which players should start. */
  def first: Tile

  /** Compares two boards. */
  def ==(obj: Board): Boolean = tiles == obj.tiles && definition == obj.definition && first == obj.first

  override def equals(obj: Any): Boolean = obj match {
    case b: Board => b == this
    case _ => false
  }

  override def toString: String = this.getClass.getSimpleName +
    " (definition:" + definition + ", first: " +
    first + ", tiles: " + tiles + ")"
}

object Board {

  /** Factory method that takes the definition as input. */
  def apply(board: BoardDefinition): Board = new BoardImpl(board)

  private class BoardImpl(val definition: BoardDefinition) extends Board {

    private val tileMap: Map[TileDefinition, Tile] = definition.tiles map (t => t -> Tile(t)) toMap

    override def tiles: Set[Tile] = tileMap.values.toSet

    override def next(tile: Tile): Option[Tile] = definition next tile.definition map (tileMap(_))

    override def first: Tile = tileMap(definition first)

    override def prev(tile: Tile): Option[Tile] = definition prev tile.definition map (tileMap(_))
  }

}
