package dsl.words

import dsl.properties.board.BoardHasBuilder

class GameWord() {
  def apply(board: BoardWord): BoardHasBuilder = board.builder
}
