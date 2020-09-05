package dsl.board.words

import dsl.nodes.RuleBook

class TilesWord() {

  def apply(n: Int*)(implicit ruleBook: RuleBook): TilesHaveBuilder = TilesHaveBuilder(n, ruleBook.boardBuilder, ruleBook.graphicMap)

}
