package untitled.goose.framework.dsl.rules.actions.words

import untitled.goose.framework.dsl.rules.actions.nodes.ActionRuleNode.{ActionRuleNode, ActionRuleNodeImpl}
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.GameEvent

case class NamedAction(name: String, when: GameState => Boolean, trigger: GameState => GameEvent, allow: Boolean) {
  def priority(priority: Int): ActionRuleNode = ActionRuleNodeImpl(name, when, trigger, priority, allow)
}
