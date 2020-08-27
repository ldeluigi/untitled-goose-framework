package dsl.words

import dsl.nodes.RuleBook
import dsl.properties.board.BoardHasBuilder

class GameWord() {
  def apply(board: BoardWord)(implicit ruleBook: RuleBook): BoardHasBuilder = BoardHasBuilder(ruleBook.boardBuilder)
}
