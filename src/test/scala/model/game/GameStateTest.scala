package model.game

import mock.MatchMock
import model.entities.board.{Board, Disposition, Piece}
import model.{Color, Player}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GameStateTest extends AnyFlatSpec with Matchers {

  var gameMatch: Game = MatchMock.default
  val gameState: GameState = gameMatch.currentState

  val board: Board = Board(10, Disposition.snake(10))
  val p1: Player = Player("P1")
  val p2: Player = Player("P2")
  val players: Map[Player, Piece] = Map(p1 -> Piece(Color.Red), p2 -> Piece(Color.Blue))

  val gameBoard: GameBoard = gameState.gameBoard

  behavior of "GameStateTest"

  it should "currentCycle" in {
    gameState.currentCycle should be(0)
  }

  it should "nextPlayer" in {
    gameState.nextPlayer should equal(p2)
  }

  it should "currentPlayer" in {
    gameState.currentPlayer should equal(p1)
  }

  it should "playerPieces" in {
    gameState.playerPieces should equal(players)
  }

  it should "players" in {
    gameState.players should equal(players.keys.toSet)
  }

  it should "gameHistory" in {
    gameState.gameHistory.size should be(0)
  }

  it should "currentTurn" in {
    gameState.currentTurn should be(0)
  }

  it should "consumableEvents" in {
    gameState.consumableBuffer.size should be(0)
  }

  it should "gameBoard" in {
    gameState.gameBoard should equal(gameBoard)
  }

}
