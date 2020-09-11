package untitled.goose.framework.dsl

import untitled.goose.framework.dsl.events.words.KeyValueSetter
import untitled.goose.framework.dsl.rules.players.PlayerRangeWord

trait Implicits {

  implicit def rangeToPlayerNumber(range: Range): PlayerRangeWord = PlayerRangeWord(range)

  implicit def stringToKeyValue(string: String): KeyValueSetter = KeyValueSetter(string)

}
