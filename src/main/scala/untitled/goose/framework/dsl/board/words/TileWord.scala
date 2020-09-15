package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.nodes.RuleBook

class TileWord() {

  def apply(n: Int)(implicit ruleBook: RuleBook): TileHasBuilder =
    TileHasBuilder(n, ruleBook.boardBuilder, ruleBook.graphicMap)

  def apply(name: String)(implicit ruleBook: RuleBook): TileHasBuilder =
    TileHasBuilder(name, ruleBook.boardBuilder, ruleBook.graphicMap)
}

