package untitled.goose.framework.dsl.rules.actions.words

import untitled.goose.framework.dsl.rules.actions.nodes.ActionRuleNode.{ActionRuleNode, ActionRuleNodeImpl}
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.GameEvent

/**
 * Creates a named action.
 * @param name the name of the action.
 * @param when when this action rule should be activated.
 * @param trigger creates the event related to this action.
 * @param allow if true, the action is allowed, otherwise is negated.
 */
case class NamedAction(name: String, when: GameState => Boolean, trigger: GameState => GameEvent, allow: Boolean) {

  /** Enables "[action] priority [number]" */
  def priority(priority: Int): ActionRuleNode = ActionRuleNodeImpl(name, when, trigger, priority, allow)
}
