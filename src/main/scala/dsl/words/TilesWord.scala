package dsl.words

import dsl.properties.tile.TilesHaveProperty

class TilesWord() {

  def apply(n: Int*): TilesHaveProperty = new TilesHaveProperty {}

}
