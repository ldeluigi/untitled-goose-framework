package dsl.words

import dsl.nodes.RuleBook
import dsl.properties.tile.TileHasBuilder

class TileWord() {

  def apply(n: Int)(implicit ruleBook: RuleBook): TileHasBuilder = TileHasBuilder(n, ruleBook.boardBuilder, ruleBook.graphicMap)

  def apply(name: String)(implicit ruleBook: RuleBook): TileHasBuilder = TileHasBuilder(name, ruleBook.boardBuilder, ruleBook.graphicMap)
}

