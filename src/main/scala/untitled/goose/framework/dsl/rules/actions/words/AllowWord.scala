package untitled.goose.framework.dsl.rules.actions.words

import untitled.goose.framework.dsl.UtilityWords.ToWord
import untitled.goose.framework.dsl.events.words.CustomEventInstance
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.actions.words.custom.UnnamedCustomAction
import untitled.goose.framework.dsl.rules.actions.words.dice.DiceWord
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.GameEvent

import scala.reflect.ClassTag

class AllowWord(when: GameState => Boolean)(implicit ruleBook: RuleBook) {
  def apply(to: ToWord): AllowWord = this

  def trigger[T: ClassTag](customEventInstance: CustomEventInstance[GameState]): UnnamedCustomAction = UnnamedCustomAction(when, customEventInstance, allow = true)

  def trigger(trigger: GameState => GameEvent): UnnamedAction = UnnamedAction(when, trigger, allow = true)

  def use(refName: String): RefAction = RefAction(when, allow = true, Set(refName))

  def use(refNames: String*): RefAction = RefAction(when, allow = true, refNames.toSet)

  def roll(number: Int): DiceWord = dice.DiceWord(when, allow = true, number)
}
