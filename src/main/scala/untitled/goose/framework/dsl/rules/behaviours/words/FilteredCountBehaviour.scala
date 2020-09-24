package untitled.goose.framework.dsl.rules.behaviours.words

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.behaviours.nodes.BehaviourNode
import untitled.goose.framework.dsl.rules.operations.nodes.OperationNode
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.reflect.ClassTag

/** Used for "[behaviour filter] [then] ..." */
case class FilteredCountBehaviour[T <: ConsumableGameEvent : ClassTag]
(
  condition: GameState => Boolean,
  filterStrategy: T => Boolean,
  countStrategy: Int => Boolean,
) {

  /** Enables "... resolve([operation], [operation]) ..." */
  def resolve(operations: OperationNode[T]*)(implicit ruleBook: RuleBook): BehaviourNode[T] = {
    val b = BehaviourNode[T](condition, filterStrategy, countStrategy, operations: _*)
    ruleBook.ruleSet.behaviourCollectionNode.addBehaviourNode(b)
    b
  }

}
