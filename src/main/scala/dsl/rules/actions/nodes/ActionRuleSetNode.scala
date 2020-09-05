package dsl.rules.actions.nodes

import dsl.nodes.RuleBookNode
import dsl.rules.actions.nodes.ActionRuleNode.{ActionRuleWithActionNode, ActionRuleWithRefNode}
import model.actions.Action
import model.rules.actionrules.ActionRule

trait ActionRuleSetNode extends RuleBookNode with ActionCollection {

  def addActionRuleNode(actionRuleNode: ActionRuleNode): Unit

  def actionRules: Set[ActionRule]
}

object ActionRuleSetNode {

  private class ActionRuleSetNodeImpl() extends ActionRuleSetNode {
    private var nodes: Set[ActionRuleNode] = Set()

    private var actions: Map[String, Action] = Map()

    override def addActionRuleNode(actionRuleNode: ActionRuleNode): Unit = {
      nodes += actionRuleNode
      actionRuleNode match {
        case a: ActionRuleWithActionNode => registerAction(a.generateAction())
        case _ => Unit
      }
    }

    override def actionRules: Set[ActionRule] =
      nodes.map {
        case a: ActionRuleWithActionNode => a.generateActionRule()
        case a: ActionRuleWithRefNode => a.generateActionRule()
      }

    override def check: Seq[String] = nodes.toSeq.flatMap(_.check)

    override def isActionDefined(name: String): Boolean = actions contains name

    private def registerAction(action: Action): Unit = actions += action.name -> action

    override def getAction(name: String): Action = actions(name)
  }

  def apply(): ActionRuleSetNode = new ActionRuleSetNodeImpl()
}
