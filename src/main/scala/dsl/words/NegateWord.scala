package dsl.words

import dsl.nodes.RuleBook
import model.entities.runtime.GameState

class NegateWord(when: GameState => Boolean)(implicit ruleBook: RuleBook) {
  def apply(to: ToWord): NegateWord = this

  def use(refName: String): RefAction = RefAction(when, allow = false, Set(refName))
}
