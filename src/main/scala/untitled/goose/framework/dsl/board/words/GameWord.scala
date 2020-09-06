package untitled.goose.framework.dsl.board.words

import untitled.goose.framework.dsl.UtilityWords.BoardWord
import untitled.goose.framework.dsl.nodes.RuleBook

class GameWord() {
  def apply(board: BoardWord)(implicit ruleBook: RuleBook): BoardHasBuilder = BoardHasBuilder(ruleBook.boardBuilder)
}
