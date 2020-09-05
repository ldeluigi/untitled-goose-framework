package dsl.words.ruleset.action

import dsl.nodes.RuleBook
import dsl.words.ruleset.action
import model.entities.runtime.GameState
import model.events.GameEvent

class AllowWord(when: GameState => Boolean)(implicit ruleBook: RuleBook) {
  def apply(to: ToWord): AllowWord = this

  def trigger(trigger: GameState => GameEvent): UnnamedAction = action.UnnamedAction(when, trigger, allow = true)

  def use(refName: String): RefAction = action.RefAction(when, allow = true, Set(refName))

  def use(refNames: String*): RefAction = action.RefAction(when, allow = true, refNames.toSet)
}
