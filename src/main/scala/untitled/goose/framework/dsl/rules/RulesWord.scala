package untitled.goose.framework.dsl.rules

import untitled.goose.framework.dsl.nodes.RuleBook

class RulesWord {

  def of(name: String)(implicit ruleBook: RuleBook): Unit =
    ruleBook.setGameName(name)

}
