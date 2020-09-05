package dsl.rules.actions.words

import dsl.nodes.RuleBook
import dsl.rules.actions.words
import model.entities.runtime.GameState
import model.events.GameEvent

class AllowWord(when: GameState => Boolean)(implicit ruleBook: RuleBook) {
  def apply(to: ToWord): AllowWord = this

  def trigger(trigger: GameState => GameEvent): UnnamedAction = words.UnnamedAction(when, trigger, allow = true)

  def use(refName: String): RefAction = words.RefAction(when, allow = true, Set(refName))

  def use(refNames: String*): RefAction = words.RefAction(when, allow = true, refNames.toSet)
}
