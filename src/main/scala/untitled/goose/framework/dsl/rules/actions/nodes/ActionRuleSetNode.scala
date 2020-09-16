package untitled.goose.framework.dsl.rules.actions.nodes

import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.dsl.rules.actions.nodes.ActionRuleNode.{ActionGeneration, ActionRuleNode}
import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.rules.actionrules.ActionRule

private[dsl] trait ActionRuleSetNode extends RuleBookNode with ActionCollection {
  def addLoseTurnActionNode(loseNode: LoseTurnActionNode)

  def addActionRuleNode(actionRuleNode: ActionRuleNode): Unit

  def actionRules: Set[ActionRule]

  def allActions: Set[Action]
}

object ActionRuleSetNode {

  private class ActionRuleSetNodeImpl() extends ActionRuleSetNode {
    private var nodes: Set[ActionRuleNode] = Set()

    private var actions: Map[String, Action] = Map()

    private var loseTurnNode: Option[LoseTurnActionNode] = None

    override def addActionRuleNode(actionRuleNode: ActionRuleNode): Unit = {
      nodes += actionRuleNode
      actionRuleNode match {
        case a: ActionRuleNode with ActionGeneration if a.check.isEmpty => registerAction(a.generateAction())
        case _ => Unit
      }
    }

    override def actionRules: Set[ActionRule] =
      loseTurnNode.map(a => Set(a.generateActionRule(this.allActions))).getOrElse(Set()) ++
        nodes.map(_.generateActionRule())

    override def check: Seq[String] =
      loseTurnNode.map(_.check).getOrElse(Seq()) ++ nodes.toSeq.flatMap(_.check)


    override def isActionDefined(name: String): Boolean = actions contains name

    private def registerAction(action: Action): Unit = actions += action.name -> action

    override def getAction(name: String): Action = actions(name)

    override def allActions: Set[Action] = actions.values.toSet

    override def addLoseTurnActionNode(loseNode: LoseTurnActionNode): Unit = loseTurnNode = Some(loseNode)
  }

  def apply(): ActionRuleSetNode = new ActionRuleSetNodeImpl()
}
