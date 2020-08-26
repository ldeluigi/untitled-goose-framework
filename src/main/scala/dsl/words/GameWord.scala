package dsl.words

import dsl.board.properties.BoardHasProperty

case class GameWord() {

  def apply(board: BoardWord): BoardHasProperty = new BoardHasProperty {}
}
