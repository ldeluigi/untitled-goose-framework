package dsl.rules.actions.words

import dsl.nodes.RuleBook
import dsl.rules.actions.words
import model.entities.runtime.GameState

class NegateWord(when: GameState => Boolean)(implicit ruleBook: RuleBook) {
  def apply(to: ToWord): NegateWord = this

  def use(refName: String): RefAction = words.RefAction(when, allow = false, Set(refName))

  def roll(number: Int): DiceWord = DiceWord(when, allow = false, number)
}
