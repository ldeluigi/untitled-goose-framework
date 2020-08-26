package dsl.tile.properties

trait TileHasProperty {
  def has(name: NameProperty): TilePropertyChanger = ???
}
