package untitled.goose.framework.dsl.rules.behaviours.words

import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.reflect.ClassTag

/** Used for "... is ([numberFilter])" */
case class FilteredBehaviour[T <: ConsumableGameEvent : ClassTag](condition: GameState => Boolean, filterStrategy: T => Boolean) {

  /** Enables "... is ([numberFilter])" */
  def is(countStrategy: Int => Boolean): FilteredCountBehaviour[T] = FilteredCountBehaviour(condition, filterStrategy, countStrategy)
}
