package dsl.words.ruleset.action

import dsl.nodes.ActionRuleNode
import dsl.nodes.ActionRuleNode.ActionRuleWithActionNode
import model.entities.runtime.GameState
import model.events.GameEvent

case class NamedAction(name: String, when: GameState => Boolean, trigger: GameState => GameEvent, allow: Boolean) {
  def priority(priority: Int): ActionRuleNode = ActionRuleWithActionNode(name, when, trigger, priority, allow)
}
