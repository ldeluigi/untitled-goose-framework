package dsl

import dsl.board.properties.BoardHasProperty

class BoardWord() {

  def apply(name: String): BoardHasProperty = BoardHasProperty()
}
