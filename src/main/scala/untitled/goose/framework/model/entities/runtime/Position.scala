package untitled.goose.framework.model.entities.runtime

trait Position {
  def tile: Tile

  override def toString: String = this.getClass.getSimpleName + "(" + tile + ")"
}

object Position {

  private class PositionImpl(val tile: Tile) extends Position {

    override def equals(obj: Any): Boolean = {
      obj match {
        case obj: Position => this.tile.equals(obj.tile)
        case _ => false
      }
    }
  }

  def apply(tile: Tile): Position = new PositionImpl(tile)
}
