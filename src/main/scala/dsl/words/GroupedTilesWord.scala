package dsl.words

import dsl.properties.tile.TilesHaveProperty

case class GroupedTilesWord() {
  def apply(group: String): TilesHaveProperty = new TilesHaveProperty {}
}
