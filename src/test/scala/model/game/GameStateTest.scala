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

  it should "return its current cycle of execution" in {
    gameState.currentCycle should be(0)
  }

  it should "return who is the next player" in {
    gameState.nextPlayer should equal(p2)
  }

  it should "return its current player of execution" in {
    gameState.currentPlayer should equal(p1)
  }

  it should "return a map of players and relative picies" in {
    gameState.playerPieces should equal(players)
  }

  it should "return a seq of players" in {
    gameState.players should equal(players.keys.toSet)
  }

  it should "return a seq of game events" in {
    gameState.gameHistory.size should be(0)
  }

  it should "return the current turn of execution" in {
    gameState.currentTurn should be(0)
  }

  it should "return a seq of consumable game events" in {
    gameState.consumableBuffer.size should be(0)
  }

  it should "return its gameBoard" in {
    gameState.gameBoard should equal(gameBoard)
  }

}
