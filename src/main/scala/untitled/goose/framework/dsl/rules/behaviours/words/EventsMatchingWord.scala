package untitled.goose.framework.dsl.rules.behaviours.words

import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.reflect.ClassTag

/** Used for "events [match] ([condition]) ..." */
case class EventsMatchingWord[T <: ConsumableGameEvent : ClassTag]() {

  /** Enables "events matching ([condition]) ..." */
  def matching(condition: T => Boolean): FilterStrategy[T] = FilterStrategy(condition)
}
