package dsl

import dsl.words.PlayersWord

trait Implicits {

  implicit def rangeToPlayerNumber(range: Range): PlayersWord = PlayersWord(range)
}
