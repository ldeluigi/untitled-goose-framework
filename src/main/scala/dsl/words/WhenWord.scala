package dsl.words

import model.entities.runtime.GameState


case class WhenWord(condition: GameState => Boolean) {

  val allowed: AllowWord = new AllowWord()

  val negated: NegateWord = new NegateWord()

}
