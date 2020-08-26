package dsl

import dsl.tile.properties.TileHasProperty

class TileWord() {

  def apply(n: Int): TileHasProperty = TileHasProperty()
}
