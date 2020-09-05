package dsl.rules.actions.words

import dsl.rules.actions.nodes.ActionRuleNode
import dsl.rules.actions.nodes.ActionRuleNode.ActionRuleWithActionNode
import model.entities.runtime.GameState
import model.events.GameEvent

case class NamedAction(name: String, when: GameState => Boolean, trigger: GameState => GameEvent, allow: Boolean) {
  def priority(priority: Int): ActionRuleNode = ActionRuleWithActionNode(name, when, trigger, priority, allow)
}
