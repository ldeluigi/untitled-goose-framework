package untitled.goose.framework.dsl.rules.operations.nodes

import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent
import untitled.goose.framework.model.rules.operations.Operation

sealed trait OperationNode[T <: ConsumableGameEvent] extends RuleBookNode {
  def getOperation: Seq[T] => Operation
}


//TODO IMPLEMENTATIONS OF OPERATION NODES