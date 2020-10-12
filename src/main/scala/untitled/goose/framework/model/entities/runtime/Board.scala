package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.entities.definitions.{BoardDefinition, OneWayPath, TileDefinition}

/** At runtime, a board state is defined by tiles state. */
trait Board extends Defined[BoardDefinition] {

  /** The set of tile in this board at runtime. */
  def tiles: Map[TileDefinition, Tile]

  def tileOrdering: OneWayPath[Tile]

  /** Compares two boards. */
  def ==(obj: Board): Boolean =
    definition == obj.definition &&
      tiles == obj.tiles &&
      tileOrdering == obj.tileOrdering

  override def equals(obj: Any): Boolean = obj match {
    case b: Board => b == this
    case _ => false
  }

  override def toString: String = this.getClass.getSimpleName +
    " (definition:" + definition + ", tiles: " + tiles + ")"
}

object Board {

  case class BoardImpl(
                        definition: BoardDefinition,
                        tiles: Map[TileDefinition, Tile],
                        tileOrdering: OneWayPath[Tile]
                      ) extends Board

  /** Factory method that takes the definition as input. */
  def apply(definition: BoardDefinition): Board = {
    val tileMap = definition.tiles.map(t => t -> Tile(t)).toMap
    BoardImpl(
      definition,
      tileMap,
      OneWayPath(tileMap(definition.tileOrdering.first),
          c => definition.tileOrdering.next(c.definition).map(tileMap(_)),
          c =>definition.tileOrdering.prev(c.definition).map(tileMap(_))
      )
    )
  }
}
