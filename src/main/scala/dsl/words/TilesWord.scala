package dsl.words

import dsl.tile.properties.TilesHaveProperty

case class TilesWord() {

  def apply(n: Int*): TilesHaveProperty = new TilesHaveProperty {}

}
