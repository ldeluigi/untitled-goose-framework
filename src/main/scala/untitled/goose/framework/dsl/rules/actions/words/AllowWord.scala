package untitled.goose.framework.dsl.rules.actions.words

import untitled.goose.framework.dsl.UtilityWords.ToWord
import untitled.goose.framework.dsl.events.words.CustomEventInstance
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.actions.words.custom.UnnamedCustomAction
import untitled.goose.framework.dsl.rules.actions.words.dice.ActionDiceWord
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.GameEvent

import scala.reflect.ClassTag

class AllowWord(when: GameState => Boolean)(implicit ruleBook: RuleBook) {
  def apply(to: ToWord): AllowWord = this

  /** Enables "to trigger [custom event]" */
  def trigger[T: ClassTag](customEventInstance: CustomEventInstance[GameState]): UnnamedCustomAction = UnnamedCustomAction(when, customEventInstance)

  /** Enables "to trigger (state => [event])" */
  def trigger(trigger: GameState => GameEvent): UnnamedAction = UnnamedAction(when, trigger, allow = true)

  /** Enables "to use [action name]" */
  def use(refName: String): RefAction = RefAction(when, allow = true, Set(refName))

  /** Enables "to use ([action name], [action name], ...)" */
  def use(refNames: String*): RefAction = RefAction(when, allow = true, refNames.toSet)

  /** Enables "to roll [number] [dice] ..." */
  def roll(number: Int): ActionDiceWord = dice.ActionDiceWord(when, number)
}
