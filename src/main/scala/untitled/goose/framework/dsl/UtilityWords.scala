package untitled.goose.framework.dsl

import untitled.goose.framework.dsl.UtilityWords.{BoardWord, OnWord, ToWord, ValueWord}

trait UtilityWords {

  val on: OnWord = OnWord()
  val board: BoardWord = BoardWord()
  val value: ValueWord = ValueWord()
  val to: ToWord = ToWord()

}

object UtilityWords {

  case class OnWord()

  case class BoardWord()

  case class ValueWord()

  case class ToWord()

}
