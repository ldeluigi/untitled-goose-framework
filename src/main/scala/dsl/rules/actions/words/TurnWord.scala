package dsl.rules.actions.words

import dsl.nodes.RuleBook
import dsl.rules.actions.nodes.ActionRuleNode.ActionRuleNode

class TurnWord() {
  def apply(playersWord: PlayersAreWord) = new TurnWord

  def are(actionRules: ActionRuleNode*)(implicit ruleBook: RuleBook): Unit = {
    actionRules.foreach(ruleBook.ruleSet.actionRuleSetNode.addActionRuleNode)
  }

}
