package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.nodes.RuleBook

class GroupedTilesWord() {
  def apply(group: String)(implicit ruleBook: RuleBook): TilesHaveBuilder = TilesHaveBuilder(group, ruleBook.boardBuilder, ruleBook.graphicMap)
}
