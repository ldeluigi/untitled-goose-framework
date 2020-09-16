package untitled.goose.framework.dsl.rules.actions.words

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.behaviours.words.{FilterStrategy, FilteredBehaviour}
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.reflect.ClassTag

case class WhenWord(condition: GameState => Boolean)(implicit ruleBook: RuleBook) {

  val allowed: AllowWord = new AllowWord(condition)

  val negated: NegateWord = new NegateWord(condition)

  def and[T <: ConsumableGameEvent : ClassTag](filterStrategy: FilterStrategy[T]): FilteredBehaviour[T] =
    FilteredBehaviour(condition, filterStrategy.strategy)

  def when[T <: ConsumableGameEvent : ClassTag](filterStrategy: FilterStrategy[T]): FilteredBehaviour[T] = and(filterStrategy)

}
