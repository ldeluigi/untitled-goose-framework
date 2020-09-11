package untitled.goose.framework.dsl.rules.behaviours.words

import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.reflect.ClassTag

case class EventsMatchingWord[T <: ConsumableGameEvent : ClassTag]() {

  def matching(condition: T => Boolean): FilterStrategy[T] = FilterStrategy(condition)
}
