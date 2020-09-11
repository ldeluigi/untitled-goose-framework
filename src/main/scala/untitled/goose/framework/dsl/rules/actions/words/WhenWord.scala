package untitled.goose.framework.dsl.rules.actions.words

import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.behaviours.words
import untitled.goose.framework.dsl.rules.behaviours.words.FilteredBehaviour
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.reflect.ClassTag

case class WhenWord(condition: GameState => Boolean)(implicit ruleBook: RuleBook) {

  val allowed: AllowWord = new AllowWord(condition)

  val negated: NegateWord = new NegateWord(condition)

  def filter[T <: ConsumableGameEvent : ClassTag](filterStrategy: T => Boolean): FilteredBehaviour[T] = words.FilteredBehaviour(condition, filterStrategy)
}
