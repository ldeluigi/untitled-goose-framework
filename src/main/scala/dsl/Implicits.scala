package dsl

import dsl.words.TilesWord

trait Implicits {
  implicit def numToTileWord(num: Int): TilesWord = new TilesWord(num)
}
