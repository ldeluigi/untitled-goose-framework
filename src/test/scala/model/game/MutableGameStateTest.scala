package model.game

import mock.MatchMock
import model.entities.board.Piece
import model.{Color, Player}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MutableGameStateTest extends AnyFlatSpec with Matchers {

  var gameMatch: Game = MatchMock.default
  val gameMutableState: MutableGameState = gameMatch.currentState

  val p1: Player = Player("P1")
  val piece: Piece = Piece(Color.Blue)

  behavior of "MutableGameStateTest"

  "MutableGameState.currentTurn" should "return the current turn of execution" in {
    gameMutableState.currentTurn should be(0)
  }

  "MutableGameState.gameHistory" should "return a seq of game events" in {
    gameMutableState.gameHistory.size should be(0)
  }

  "MutableGameState.currentPlayer" should "return its current player of execution" in {
    gameMutableState.currentPlayer should equal(p1)
  }

  "MutableGameState.playerPieces" should "update a player pieces as told and check correctness" in {
    gameMutableState.updatePlayerPiece(p1, _ => piece)
    gameMutableState.playerPieces(p1).color should equal(piece.color)
  }

  "MutableGameState.consumableBuffer" should "return a seq of consumable game events" in {
    gameMutableState.consumableBuffer.size should be(0)
  }

}
