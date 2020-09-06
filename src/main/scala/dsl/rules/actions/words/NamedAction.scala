package dsl.rules.actions.words

import dsl.rules.actions.nodes.ActionRuleNode.{ActionRuleNode, ActionRuleNodeImpl}
import model.entities.runtime.GameState
import model.events.GameEvent

case class NamedAction(name: String, when: GameState => Boolean, trigger: GameState => GameEvent, allow: Boolean) {
  def priority(priority: Int): ActionRuleNode = ActionRuleNodeImpl(name, when, trigger, priority, allow)
}
