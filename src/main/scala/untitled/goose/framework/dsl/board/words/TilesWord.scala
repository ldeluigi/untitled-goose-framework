package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.nodes.RuleBook

/** Used for "tiles [identifiers] [verb] ..." */
class TilesWord() {

  /** Enables "tiles ([number], [number], ...) [verb] ..." */
  def apply(n: Int*)(implicit ruleBook: RuleBook): TilesHaveBuilder = TilesHaveBuilder(n, ruleBook.boardBuilder, ruleBook.graphicMap)

  /** Enables "tiles ([number] to [number]) [verb] ..." */
  def apply(range: Range)(implicit ruleBook: RuleBook): TilesHaveBuilder = TilesHaveBuilder(range, ruleBook.boardBuilder, ruleBook.graphicMap)

}
