package untitled.goose.framework.dsl.rules.behaviours.nodes

import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.model.rules.behaviours.BehaviourRule

trait BehaviourCollectionNode extends RuleBookNode {
  def behaviours: Seq[BehaviourRule]

  def addBehaviourNode(behaviourNode: BehaviourNode[_]): Unit
}

//TODO Should this also manage system behaviours?
object BehaviourCollectionNode {

  private class BehaviourCollectionNodeImpl() extends BehaviourCollectionNode {

    private var nodes: Seq[BehaviourNode[_]] = Seq()

    def addBehaviourNode(behaviourNode: BehaviourNode[_]): Unit =
      nodes :+= behaviourNode

    override def behaviours: Seq[BehaviourRule] =
      nodes.map(_.generateBehaviour)

    override def check: Seq[String] = ??? //TODO implement check
  }

  def apply(): BehaviourCollectionNode = new BehaviourCollectionNodeImpl()
}

