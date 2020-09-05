package dsl.board.words

import dsl.nodes.RuleBook

class TileWord() {

  def apply(n: Int)(implicit ruleBook: RuleBook): TileHasBuilder = TileHasBuilder(n, ruleBook.boardBuilder, ruleBook.graphicMap)

  def apply(name: String)(implicit ruleBook: RuleBook): TileHasBuilder = TileHasBuilder(name, ruleBook.boardBuilder, ruleBook.graphicMap)
}

