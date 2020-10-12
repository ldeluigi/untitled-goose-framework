package untitled.goose.framework.dsl.rules.actions.words.custom

import untitled.goose.framework.dsl.events.words.CustomEventInstance
import untitled.goose.framework.dsl.rules.actions.nodes.ActionRuleNode.{ActionRuleNode, CustomEventActionNode}
import untitled.goose.framework.model.entities.runtime.GameState

/**
 * Creates a custom action.
 *
 * @param name        the name of the action.
 * @param when        the condition that enables the action.
 * @param customEvent the custom event created by this action.
 */
case class NamedCustomAction(name: String, when: GameState => Boolean, customEvent: CustomEventInstance[GameState]) {

  /** Enables "[action definition] priority [number]" */
  def priority(priority: Int): ActionRuleNode = CustomEventActionNode(name, when, customEvent, priority)
}
