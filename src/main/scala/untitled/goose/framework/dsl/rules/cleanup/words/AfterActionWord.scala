package untitled.goose.framework.dsl.rules.cleanup.words

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.model.entities.runtime.MutableGameState
import untitled.goose.framework.model.rules.cleanup.CleanupRule

/** Used for specifying cleanup operations after "action" */
case class AfterActionWord() {

  /** Enables "... action([cleanup operation], [cleanup operation], ...)" */
  def action(cleanups: (MutableGameState => Unit)*)(implicit ruleBook: RuleBook): Unit = {
    ruleBook.ruleSet.cleanupRules ++= cleanups.map(CleanupRule(_))
  }
}
