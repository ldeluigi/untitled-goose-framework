package untitled.goose.framework.dsl.rules.cleanup.words

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.entities.runtime.MutableGameState

case class AfterActionWord() {
  def action(cleanups: (MutableGameState => Unit)*)(implicit ruleBook: RuleBook): Unit = {
    ruleBook.ruleSet.cleanupRules ++:= cleanups
  }
}
