package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.nodes.RuleBook

class TilesWord() {

  def apply(n: Int*)(implicit ruleBook: RuleBook): TilesHaveBuilder = TilesHaveBuilder(n, ruleBook.boardBuilder, ruleBook.graphicMap)

}
