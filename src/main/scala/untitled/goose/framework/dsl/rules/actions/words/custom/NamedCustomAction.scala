package untitled.goose.framework.dsl.rules.actions.words.custom

import untitled.goose.framework.dsl.events.words.CustomEventInstance
import untitled.goose.framework.dsl.rules.actions.nodes.ActionRuleNode.{ActionRuleNode, CustomEventActionNode}
import untitled.goose.framework.model.entities.runtime.GameState

case class NamedCustomAction(name: String, when: GameState => Boolean, customEvent: CustomEventInstance[GameState], allow: Boolean) {
  def priority(priority: Int): ActionRuleNode = CustomEventActionNode(name, when, customEvent, priority, allow)
}
