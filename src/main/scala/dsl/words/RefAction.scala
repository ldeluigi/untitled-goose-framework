package dsl.words

import dsl.nodes.ActionRuleNode
import dsl.nodes.ActionRuleNode.ActionRuleWithRefNode
import model.entities.runtime.GameState

case class RefAction(when: GameState => Boolean, allow: Boolean, refName: Set[String]) {
  def priority(priority: Int): ActionRuleNode = ActionRuleWithRefNode(when, priority, allow, refName)
}
