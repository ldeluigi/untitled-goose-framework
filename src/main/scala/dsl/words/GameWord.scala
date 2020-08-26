package dsl.words

import dsl.properties.board.BoardHasProperty

class GameWord() {

  def apply(board: BoardWord): BoardHasProperty = new BoardHasProperty {}
}
