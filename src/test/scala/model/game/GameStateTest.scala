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

  it should "have its current cycle" in {
    gameState.currentCycle should be(0)
  }

  it should "know who is the next player" in {
    gameState.nextPlayer should equal(p2)
  }

  it should "have the current player" in {
    gameState.currentPlayer should equal(p1)
  }

  it should "have pieces" in {
    gameState.playerPieces should equal(players)
  }

  it should "have a set of players" in {
    gameState.players should equal(players.keys.toSet)
  }

  it should "have a game events history" in {
    gameState.gameHistory.size should be(0)
  }

  it should "have the current turn" in {
    gameState.currentTurn should be(0)
  }

  it should "have its buffer of consumable events of size" in {
    gameState.consumableBuffer.size should be(0)
  }

  it should "have its own gameboard" in {
    gameState.gameBoard should equal(gameBoard)
  }

}
