package dsl

import dsl.words.TilesWord

trait Implicits {
  implicit def numToTilesWord(num: Int): TilesWord = new TilesWord(num)
  implicit def numToTileIdentifier(num: Int): TileIdentifierByPosition = new TileIdentifierByPosition(num)
}
