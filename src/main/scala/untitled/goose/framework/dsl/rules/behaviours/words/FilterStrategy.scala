package untitled.goose.framework.dsl.rules.behaviours.words

import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.reflect.ClassTag

case class FilterStrategy[T <: ConsumableGameEvent : ClassTag](strategy: T => Boolean)
