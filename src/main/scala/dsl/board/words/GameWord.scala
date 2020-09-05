package dsl.board.words

import dsl.UtilityWords.BoardWord
import dsl.nodes.RuleBook

class GameWord() {
  def apply(board: BoardWord)(implicit ruleBook: RuleBook): BoardHasBuilder = BoardHasBuilder(ruleBook.boardBuilder)
}
