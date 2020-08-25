package dsl.words

import dsl.RuleBook

class RulesWord {

  def of(name: String)(implicit ruleBook: RuleBook): Unit =
    ruleBook.setGameName(name)

}
