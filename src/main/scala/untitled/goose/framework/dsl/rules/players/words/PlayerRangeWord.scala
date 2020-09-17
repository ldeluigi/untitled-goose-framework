package untitled.goose.framework.dsl.rules.players.words

import untitled.goose.framework.dsl.nodes.RuleBook

case class PlayerRangeWord(range: Range) {
  def players(implicit ruleBook: RuleBook): Unit = ruleBook.ruleSet.setPlayerRange(range)
}