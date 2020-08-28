package dsl.words

import dsl.nodes.RuleBook

case class PlayersWord(range: Range) {
  def players(implicit ruleBook: RuleBook): Unit = ruleBook.ruleSet.setPlayerRange(range)
}
