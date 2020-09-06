package dsl.rules.actions.words

import dsl.nodes.RuleBook
import model.entities.runtime.GameState
import model.events.GameEvent

class AllowWord(when: GameState => Boolean)(implicit ruleBook: RuleBook) {
  def apply(to: ToWord): AllowWord = this

  def trigger(trigger: GameState => GameEvent): UnnamedAction = UnnamedAction(when, trigger, allow = true)

  def use(refName: String): RefAction = RefAction(when, allow = true, Set(refName))

  def use(refNames: String*): RefAction = RefAction(when, allow = true, refNames.toSet)

  def roll(number: Int): DiceWord = DiceWord(when, allow = true, number)
}
