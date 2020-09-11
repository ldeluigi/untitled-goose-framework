package untitled.goose.framework.dsl.rules.behaviours.words

import untitled.goose.framework.dsl.rules.behaviours.nodes.BehaviourNode
import untitled.goose.framework.dsl.rules.behaviours.words.BehaviourNodeState.BaseState
import untitled.goose.framework.dsl.rules.operations.nodes.OperationNode
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.reflect.ClassTag

case class FilteredCountBehaviour[T <: ConsumableGameEvent : ClassTag]
(
  condition: GameState => Boolean,
  filterStrategy: T => Boolean,
  countStrategy: Int => Boolean,
) {

  def resolve(operations: OperationNode[T]*): BehaviourNode[BaseState] = ???
}
