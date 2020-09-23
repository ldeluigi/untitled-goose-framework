package untitled.goose.framework.dsl.rules.actions.words

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.behaviours.words.{FilterStrategy, FilteredBehaviour}
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.reflect.ClassTag

/** Used for "when([condition]) [and ...?] allowed|negated ..." */
case class WhenWord(condition: GameState => Boolean)(implicit ruleBook: RuleBook) {

  /** Enables "[when] allowed to [action] ..." */
  val allowed: AllowWord = new AllowWord(condition)

  /** Enables "[when] negated to [action] ..." */
  val negated: NegateWord = new NegateWord(condition)

  /** Enables "[when] and [when] ..." */
  def and[T <: ConsumableGameEvent : ClassTag](filterStrategy: FilterStrategy[T]): FilteredBehaviour[T] =
    FilteredBehaviour(condition, filterStrategy.strategy)

  /** Enables "[when] when [when] ..." */
  def when[T <: ConsumableGameEvent : ClassTag](filterStrategy: FilterStrategy[T]): FilteredBehaviour[T] = and(filterStrategy)

}
