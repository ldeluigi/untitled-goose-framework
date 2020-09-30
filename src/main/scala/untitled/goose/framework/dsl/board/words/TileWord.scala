package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.nodes.RuleBook

/** Used for "tile [identifier] [verb] ..." */
class TileWord() {

  /** Enables "tile [number] [verb] ..." */
  def apply(n: Int)(implicit ruleBook: RuleBook): TileHasBuilder =
    TileHasBuilder(n, ruleBook.boardBuilder, ruleBook.graphicMap)

  /** Enables "tile [name] [verb] ..." */
  def apply(name: String)(implicit ruleBook: RuleBook): TileHasBuilder =
    TileHasBuilder(name, ruleBook.boardBuilder, ruleBook.graphicMap)
}

