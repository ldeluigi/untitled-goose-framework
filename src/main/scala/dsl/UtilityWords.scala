package dsl

import dsl.UtilityWords.{BoardWord, OnWord}

trait UtilityWords {

  val on: OnWord = OnWord()
  val board: BoardWord = BoardWord()
}

object UtilityWords {

  case class OnWord()

  case class BoardWord()

}
