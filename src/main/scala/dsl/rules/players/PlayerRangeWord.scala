package dsl.rules.players

import dsl.nodes.RuleBook

case class PlayerRangeWord(range: Range) {
  def players(implicit ruleBook: RuleBook): Unit = ruleBook.ruleSet.setPlayerRange(range)
}
