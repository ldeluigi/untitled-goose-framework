package engine.`match`

import model.{MatchState, Player}
import model.entities.board.Board
import model.rules.RuleSet

trait Match {
  def board: Board

  def players: Set[Player]

  def state: MatchState

  def rules: RuleSet
}

object Match{
  def apply() = MockMatch()
}

case class MockMatch() extends Match{
  override def board: Board =

  override def players: Set[Player] = ???

  override def state: MatchState = ???

  override def rules: RuleSet = ???
}
