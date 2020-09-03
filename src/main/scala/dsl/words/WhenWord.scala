package dsl.words

import model.game.GameState

case class WhenWord(condition: GameState => Boolean) {

  val allowed: AllowWord = new AllowWord()

  val negated: NegateWord = new NegateWord()

}
