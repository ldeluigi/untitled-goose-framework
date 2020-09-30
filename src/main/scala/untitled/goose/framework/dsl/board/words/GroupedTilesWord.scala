package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.nodes.RuleBook

/** Used for "tiles have ..." */
class GroupedTilesWord() {

  /** Enables "tiles have ..." */
  def apply(group: String)(implicit ruleBook: RuleBook): TilesHaveBuilder = TilesHaveBuilder(group, ruleBook.boardBuilder, ruleBook.graphicMap)
}
