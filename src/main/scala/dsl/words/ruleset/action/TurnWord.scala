package dsl.words.ruleset.action

import dsl.nodes.{ActionRuleNode, RuleBook}

class TurnWord() {
  def apply(playersWord: PlayersAreWord) = new TurnWord

  def are(actionRules: ActionRuleNode*)(implicit ruleBook: RuleBook): Unit = {
    actionRules.foreach(ruleBook.ruleSet.actionRuleSetNode.addActionRuleNode)
  }

}
