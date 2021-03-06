package untitled.goose.framework.dsl.rules.actions.words

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.actions.nodes.ActionRuleNode.{ActionRuleNode, ActionRuleWithRefNode}
import untitled.goose.framework.model.entities.runtime.GameState

/** A reference to another action. */
case class RefAction(when: GameState => Boolean, allow: Boolean, refName: Set[String])(implicit ruleBook: RuleBook) {

  /** Enables "[action name] priority [number]" */
  def priority(priority: Int): ActionRuleNode = ActionRuleWithRefNode(when, priority, allow, refName, ruleBook.ruleSet.actionRuleSetNode)
}
