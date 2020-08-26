package dsl.words

import dsl.tile.properties.TileHasProperty

class TileWord() {

  def apply(n: Int): TileHasProperty = new TileHasProperty {}
}
