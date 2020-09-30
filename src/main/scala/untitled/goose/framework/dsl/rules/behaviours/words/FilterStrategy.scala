package untitled.goose.framework.dsl.rules.behaviours.words

import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.reflect.ClassTag

/** Wraps a filter strategy for a consumable event. */
case class FilterStrategy[T <: ConsumableGameEvent : ClassTag](strategy: T => Boolean)
