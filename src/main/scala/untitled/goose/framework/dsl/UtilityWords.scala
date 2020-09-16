package untitled.goose.framework.dsl

import untitled.goose.framework.dsl.UtilityWords._

trait UtilityWords {

  val on: OnWord = OnWord()
  val board: BoardWord = BoardWord()
  val value: ValueWord = ValueWord()
  val to: ToWord = ToWord()
  val each: EachWord = EachWord()
  val system: SystemWord = SystemWord()
  val priority: PriorityWord = PriorityWord()
}

object UtilityWords {

  case class EachWord()

  case class OnWord()

  case class BoardWord()

  case class ValueWord()

  case class ToWord()

  case class SystemWord()

  case class PriorityWord()

}
