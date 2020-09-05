package dsl.words.ruleset.action

import dsl.nodes.ActionRuleNode.ActionRuleWithRefNode
import dsl.nodes.{ActionRuleNode, RuleBook}
import model.entities.runtime.GameState

case class RefAction(when: GameState => Boolean, allow: Boolean, refName: Set[String])(implicit ruleBook: RuleBook) {
  def priority(priority: Int): ActionRuleNode = ActionRuleWithRefNode(when, priority, allow, refName, ruleBook.ruleSet.actionRuleSetNode)
}
