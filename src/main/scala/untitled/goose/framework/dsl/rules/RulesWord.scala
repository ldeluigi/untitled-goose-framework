package untitled.goose.framework.dsl.rules

import untitled.goose.framework.dsl.nodes.RuleBook

/** Used for "Rules ..." */
class RulesWord {

  /** Enables "Rules of [game name]" title. */
  def of(name: String)(implicit ruleBook: RuleBook): Unit =
    ruleBook.setGameName(name)

}
