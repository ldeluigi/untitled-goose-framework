package dsl

import dsl.events.words.KeyValueSetter
import dsl.rules.players.PlayerRangeWord

trait Implicits {

  implicit def rangeToPlayerNumber(range: Range): PlayerRangeWord = PlayerRangeWord(range)

  implicit def stringToKeyValue(string: String): KeyValueSetter = KeyValueSetter(string)
}
