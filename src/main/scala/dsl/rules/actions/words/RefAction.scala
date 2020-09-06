package dsl.rules.actions.words

import dsl.nodes.RuleBook
import dsl.rules.actions.nodes.ActionRuleNode.{ActionRuleNode, ActionRuleWithRefNode}
import model.entities.runtime.GameState

case class RefAction(when: GameState => Boolean, allow: Boolean, refName: Set[String])(implicit ruleBook: RuleBook) {
  def priority(priority: Int): ActionRuleNode = ActionRuleWithRefNode(when, priority, allow, refName, ruleBook.ruleSet.actionRuleSetNode)
}
