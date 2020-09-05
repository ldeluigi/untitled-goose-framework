package dsl.words.ruleset.action

import dsl.nodes.RuleBook

class RulesWord {

  def of(name: String)(implicit ruleBook: RuleBook): Unit =
    ruleBook.setGameName(name)

}
