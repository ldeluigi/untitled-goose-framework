package dsl.words

import dsl.tile.properties.TileHasProperty

case class TileWord() {

  def apply(n: Int): TileHasProperty = ???

  def apply(name: String): TileHasProperty = ???
}

