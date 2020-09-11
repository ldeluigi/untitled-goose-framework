package untitled.goose.framework.dsl.rules.behaviours.words

import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent
import untitled.goose.framework.model.rules.operations.Operation

import scala.reflect.ClassTag

case class FilteredCountBehaviour[T <: ConsumableGameEvent : ClassTag]
(
  condition: GameState => Boolean,
  filterStrategy: T => Boolean,
  countStrategy: Int => Boolean,
) {

  def resolve(operations: (Seq[T], GameState) => Operation*): Unit = ???
}
