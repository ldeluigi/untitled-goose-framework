package dsl

import dsl.words.PlayerRangeWord

trait Implicits {

  implicit def rangeToPlayerNumber(range: Range): PlayerRangeWord = PlayerRangeWord(range)
}
