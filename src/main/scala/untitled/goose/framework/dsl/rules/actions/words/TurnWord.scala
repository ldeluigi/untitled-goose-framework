package untitled.goose.framework.dsl.rules.actions.words

import untitled.goose.framework.dsl.UtilityWords.PlayersAreWord
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.actions.nodes.ActionRuleNode.ActionRuleNode

class TurnWord() {
  def apply(playersWord: PlayersAreWord) = new TurnWord

  /** Enables "players are ([action rule],[action rule], ...)" */
  def are(actionRules: ActionRuleNode*)(implicit ruleBook: RuleBook): Unit = {
    actionRules.foreach(ruleBook.ruleSet.actionRuleSetNode.addActionRuleNode)
  }

}
