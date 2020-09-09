package untitled.goose.framework.dsl.rules.actions.nodes

import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.dsl.rules.actions.nodes.ActionRuleNode.{ActionGeneration, ActionRuleNode}
import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.rules.actionrules.ActionRule

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
        case a: ActionRuleNode with ActionGeneration if a.check.isEmpty => registerAction(a.generateAction())
        case _ => Unit
      }
    }

    override def actionRules: Set[ActionRule] = nodes.map(_.generateActionRule())

    override def check: Seq[String] = nodes.toSeq.flatMap(_.check)

    override def isActionDefined(name: String): Boolean = actions contains name

    private def registerAction(action: Action): Unit = actions += action.name -> action

    override def getAction(name: String): Action = actions(name)
  }

  def apply(): ActionRuleSetNode = new ActionRuleSetNodeImpl()
}
