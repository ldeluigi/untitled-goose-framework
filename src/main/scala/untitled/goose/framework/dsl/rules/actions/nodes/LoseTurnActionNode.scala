package untitled.goose.framework.dsl.rules.actions.nodes

import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.rules.actionrules.{ActionRule, LoseTurnActionRule}

/**
 * This is a special ActionRule node because it needs all the other actions that may be negated from this rule.
 *
 * @param priority the priority of negation of this rule.
 */
case class LoseTurnActionNode(priority: Int) extends RuleBookNode {
  override def check: Seq[String] = Seq()

  def generateActionRule(actionSet: Set[Action]): ActionRule =
    LoseTurnActionRule(actionSet, priority)
}
