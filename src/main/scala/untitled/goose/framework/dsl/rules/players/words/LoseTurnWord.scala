package untitled.goose.framework.dsl.rules.players.words

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.actions.nodes.LoseTurnActionNode

case class LoseTurnWord() {

  def is(priorityValue: Int)(implicit ruleBook: RuleBook): Unit = {
    ruleBook.ruleSet.actionRuleSetNode.addLoseTurnActionNode(LoseTurnActionNode(priorityValue))
  }

}
