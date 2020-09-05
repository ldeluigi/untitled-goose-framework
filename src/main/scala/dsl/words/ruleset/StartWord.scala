package dsl.words.ruleset

import dsl.nodes.RuleBook

case class StartWord() {
  def tile(num: Int)(implicit ruleBook: RuleBook): Unit = ruleBook.ruleSet.selectFirstTileByNumber(num)

  def tile(name: String)(implicit ruleBook: RuleBook): Unit = ruleBook.ruleSet.selectFirstTileByName(name)

}
