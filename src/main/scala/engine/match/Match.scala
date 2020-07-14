package engine.`match`

import model.{MatchState, Player}
import model.entities.board.Board
import model.rules.RuleSet

trait Match {
  def board: Board

  def players: Set[Player]

  def currentState: MatchState

  def rules: RuleSet
}

