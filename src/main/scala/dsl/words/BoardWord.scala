package dsl.words

import dsl.properties.board.BoardHasBuilder

trait BoardWord {
  def builder: BoardHasBuilder
}

object BoardWord extends BoardWord {
  val builder: BoardHasBuilder = BoardHasBuilder()
}