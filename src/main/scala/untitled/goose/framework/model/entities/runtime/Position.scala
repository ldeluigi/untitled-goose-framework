package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.entities.definitions.TileDefinition

/** A position on the board. */
trait Position {

  /** The tile to which this position points. */
  def tile: TileDefinition

  override def toString: String = this.getClass.getSimpleName + "(" + tile + ")"
}

object Position {

  private class PositionImpl(val tile: TileDefinition) extends Position {

    override def equals(obj: Any): Boolean = {
      obj match {
        case obj: Position => this.tile.equals(obj.tile)
        case _ => false
      }
    }
  }

  /** Factory method to create a new position. */
  def apply(tile: TileDefinition): Position = new PositionImpl(tile)
}
