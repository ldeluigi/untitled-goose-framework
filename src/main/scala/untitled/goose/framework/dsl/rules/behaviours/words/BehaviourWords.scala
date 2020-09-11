package untitled.goose.framework.dsl.rules.behaviours.words

import untitled.goose.framework.dsl.rules.operations.words.{ForEachWord, OperationWords}
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.reflect.ClassTag

trait BehaviourWords extends OperationWords {

  def events[T <: ConsumableGameEvent : ClassTag]: EventsMatchingWord[T] = EventsMatchingWord()

  def numberOf[T <: ConsumableGameEvent : ClassTag](filterStrategy: FilterStrategy[T]): FilterStrategy[T] = filterStrategy

  def all[T <: ConsumableGameEvent : ClassTag](filterStrategy: T => Boolean): FilterStrategy[T] = FilterStrategy(filterStrategy)

  val forEach: ForEachWord = ForEachWord()

  val and: AndWord = AndWord()

  val allEvents: EventsWord = EventsWord()
}
