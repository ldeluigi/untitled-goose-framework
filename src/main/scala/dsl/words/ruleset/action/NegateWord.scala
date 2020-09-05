package dsl.words.ruleset.action

import dsl.nodes.RuleBook
import dsl.words.ruleset.action
import model.entities.runtime.GameState

class NegateWord(when: GameState => Boolean)(implicit ruleBook: RuleBook) {
  def apply(to: ToWord): NegateWord = this

  def use(refName: String): RefAction = action.RefAction(when, allow = false, Set(refName))
}
