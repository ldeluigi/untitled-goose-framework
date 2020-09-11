package untitled.goose.framework.dsl.rules.operations.nodes

import untitled.goose.framework.model.events.consumable.ConsumableGameEvent
import untitled.goose.framework.model.rules.operations.Operation

trait OperationNode[T <: ConsumableGameEvent] {
  def getOperation: Operation
}
