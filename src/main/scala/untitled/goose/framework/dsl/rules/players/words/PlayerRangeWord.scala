package untitled.goose.framework.dsl.rules.players.words

import untitled.goose.framework.dsl.nodes.RuleBook

/** Used for player range. */
case class PlayerRangeWord(range: Range) {

  /** Enables "[range] players" */
  def players(implicit ruleBook: RuleBook): Unit = ruleBook.ruleSet.setPlayerRange(range)
}
