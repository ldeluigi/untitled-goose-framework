package dsl.words

import model.entities.runtime.GameState

class NegateWord(when: GameState => Boolean) {
  def apply(to: ToWord): NegateWord = this

  def use(refName: String): RefAction = RefAction(when, allow = false, Set(refName))
}