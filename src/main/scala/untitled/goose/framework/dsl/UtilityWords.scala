package untitled.goose.framework.dsl

import untitled.goose.framework.dsl.UtilityWords.{BoardWord, OnWord}

trait UtilityWords {

  val on: OnWord = OnWord()
  val board: BoardWord = BoardWord()
}

object UtilityWords {

  case class OnWord()

  case class BoardWord()

}
