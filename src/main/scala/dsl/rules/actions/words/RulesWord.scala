package dsl.rules.actions.words

import dsl.nodes.RuleBook

class RulesWord {

  def of(name: String)(implicit ruleBook: RuleBook): Unit =
    ruleBook.setGameName(name)

}
