package dsl.board.words

import dsl.nodes.RuleBook

class GroupedTilesWord() {
  def apply(group: String)(implicit ruleBook: RuleBook): TilesHaveBuilder = TilesHaveBuilder(group, ruleBook.boardBuilder, ruleBook.graphicMap)
}
