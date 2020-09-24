package untitled.goose.framework.dsl.rules.players.words

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.actions.nodes.LoseTurnActionNode

/** Used for defining lose turn action priority. */
case class LoseTurnWord() {

  /** Enables "... loseTurn priority is [number]" */
  def is(priorityValue: Int)(implicit ruleBook: RuleBook): Unit = {
    ruleBook.ruleSet.actionRuleSetNode.addLoseTurnActionNode(LoseTurnActionNode(priorityValue))
  }

}
