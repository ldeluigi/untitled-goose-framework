package untitled.goose.framework.dsl

import untitled.goose.framework.dsl.events.words.KeyValueSetter
import untitled.goose.framework.dsl.rules.players.words.PlayerRangeWord

/** Defines implicit conversions used in the GooseDSL. */
trait Implicits {

  /** Converts a range to a range word. */
  implicit def rangeToPlayerNumber(range: Range): PlayerRangeWord = PlayerRangeWord(range)

  /** Converts a string to a property setter. */
  implicit def stringToKeyValue(string: String): KeyValueSetter = KeyValueSetter(string)

}
