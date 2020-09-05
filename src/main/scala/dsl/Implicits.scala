package dsl

import dsl.words.ruleset.PlayerRangeWord

trait Implicits {

  implicit def rangeToPlayerNumber(range: Range): PlayerRangeWord = PlayerRangeWord(range)
}
