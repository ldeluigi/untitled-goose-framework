package untitled.goose.framework.dsl.rules.behaviours.words

import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.reflect.ClassTag

trait BehaviourWords {

  def events[T <: ConsumableGameEvent : ClassTag]: EventsMatchingWord[T] = EventsMatchingWord()

  def numberOf[T <: ConsumableGameEvent : ClassTag](filterStrategy: FilterStrategy[T]): FilterStrategy[T] = filterStrategy

  def all[T <: ConsumableGameEvent : ClassTag](filterStrategy: T => Boolean): FilterStrategy[T] = FilterStrategy(filterStrategy)
}
