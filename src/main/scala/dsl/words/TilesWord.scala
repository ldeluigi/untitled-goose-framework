package dsl.words

import dsl.board.properties.TileNumProperty

class TilesWord(num: Int) {
  def tiles: TileNumProperty = TileNumProperty(num)
}


