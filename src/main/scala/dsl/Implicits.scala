package dsl

import dsl.events.KeyValueSetter
import dsl.words.ruleset.PlayerRangeWord

trait Implicits {

  implicit def rangeToPlayerNumber(range: Range): PlayerRangeWord = PlayerRangeWord(range)

  implicit def stringToKeyValue(string: String): KeyValueSetter = KeyValueSetter(string)
}
