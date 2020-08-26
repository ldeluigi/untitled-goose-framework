package dsl.tile.properties

trait TileHasProperty {
  def has(anyRef: AnyRef): Unit
}

object TileHasProperty {

  private class TileHasPropertyImpl extends TileHasProperty {
    override def has(anyRef: AnyRef): Unit = ???
  }

  def apply(): TileHasProperty = new TileHasPropertyImpl()
}
