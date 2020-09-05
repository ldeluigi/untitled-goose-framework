package dsl.words.tile

import dsl.nodes.RuleBook
import dsl.properties.tile.TilesHaveBuilder

class TilesWord() {

  def apply(n: Int*)(implicit ruleBook: RuleBook): TilesHaveBuilder = TilesHaveBuilder(n, ruleBook.boardBuilder, ruleBook.graphicMap)

}
