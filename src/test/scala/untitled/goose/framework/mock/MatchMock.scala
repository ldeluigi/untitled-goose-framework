package untitled.goose.framework.mock

import untitled.goose.framework.model.entities.runtime.{Game, Piece, Player}
import untitled.goose.framework.model.rules.ruleset.{PlayerOrdering, PriorityRuleSet}
import untitled.goose.framework.model.Color
import untitled.goose.framework.model.entities.definitions.{Board, Disposition}

object MatchMock {
  def board: Board = Board(10, Disposition.snake(10))
  def p1: Player = Player("P1")
  def p2: Player = Player("P2")
  def players: Map[Player, Piece] = Map(p1 -> Piece(Color.Red), p2 -> Piece(Color.Blue))

  def default: Game = Game(board, players, PriorityRuleSet(playerOrdering = PlayerOrdering.givenOrder(Seq(p1, p2)), admissiblePlayers = 1 to 10))
}
