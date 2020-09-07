package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.entities.definitions.{BoardDefinition, TileDefinition}

trait Board extends Defined[BoardDefinition] {

  def tiles: Set[Tile]

  def next(tile: Tile): Option[Tile]

  def prev(tile: Tile): Option[Tile]

  def first: Tile

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

  def apply(board: BoardDefinition): Board = new BoardImpl(board)

  private class BoardImpl(val definition: BoardDefinition) extends Board {

    private val tileMap: Map[TileDefinition, Tile] = definition.tiles map (t => t -> Tile(t)) toMap

    override def tiles: Set[Tile] = tileMap.values.toSet

    override def next(tile: Tile): Option[Tile] = definition next tile.definition map (tileMap(_))

    override def first: Tile = tileMap(definition first)

    override def prev(tile: Tile): Option[Tile] = definition prev tile.definition map (tileMap(_))
  }

}
