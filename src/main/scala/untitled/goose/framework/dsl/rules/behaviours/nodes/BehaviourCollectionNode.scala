package untitled.goose.framework.dsl.rules.behaviours.nodes

import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.model.rules.behaviours.BehaviourRule

/**
 * This is a collection of all defined behaviour provided from the DSL.
 */
trait BehaviourCollectionNode extends RuleBookNode {
  def behaviours: Seq[BehaviourRule]

  def addBehaviourNode(behaviourNode: BehaviourNode[_]): Unit

  def addSystemBehaviour(behaviour: BehaviourRule): Unit
}

object BehaviourCollectionNode {

  private class BehaviourCollectionNodeImpl() extends BehaviourCollectionNode {

    private var doubleSystemBehaviour = false
    private var nodes: Seq[BehaviourNode[_]] = Seq()

    private var systemBehaviours: Seq[BehaviourRule] = Seq()

    def addBehaviourNode(behaviourNode: BehaviourNode[_]): Unit =
      nodes :+= behaviourNode

    override def behaviours: Seq[BehaviourRule] =
      nodes.map(_.generateBehaviour) ++ systemBehaviours

    override def check: Seq[String] = {
      (if (doubleSystemBehaviour) {
        Seq("At least one system behaviour was included more than one time")
      } else Seq()) ++
        nodes.flatMap(_.check)
    }


    override def addSystemBehaviour(behaviour: BehaviourRule): Unit = {
      if (systemBehaviours.contains(behaviour))
        doubleSystemBehaviour = true
      systemBehaviours +:= behaviour
    }
  }

  def apply(): BehaviourCollectionNode = new BehaviourCollectionNodeImpl()
}

