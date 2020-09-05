package dsl.words.tile

import dsl.nodes.RuleBook
import dsl.properties.tile.TilesHaveBuilder

class GroupedTilesWord() {
  def apply(group: String)(implicit ruleBook: RuleBook): TilesHaveBuilder = TilesHaveBuilder(group, ruleBook.boardBuilder, ruleBook.graphicMap)
}
