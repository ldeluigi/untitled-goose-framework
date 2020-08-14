package mock

import engine.`match`.Match
import model.entities.board.{Board, Disposition, Piece}
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet}
import model.{Color, Player}

object MatchMock {
  val board: Board = Board(10, Disposition.snake(10))
  val p1: Player = Player("P1")
  val p2: Player = Player("P2")
  val players: Map[Player, Piece] = Map(p1 -> Piece(Color.Red), p2 -> Piece(Color.Blue))
  def default: Match = Match(board, players, PriorityRuleSet(playerOrdering = PlayerOrdering.givenOrder(Seq(p1, p2))))
}
