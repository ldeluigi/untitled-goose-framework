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

  it should "have a current turn" in {
    gameMutableState.currentTurn should be(0)
  }

  it should "have a happened game events history" in {
    gameMutableState.gameHistory.size should be(0)
  }

  it should "have the current player" in {
    gameMutableState.currentPlayer should equal(p1)
  }

  it should "update player pieces as told" in {
    gameMutableState.updatePlayerPiece(p1, _ => piece)
    gameMutableState.playerPieces(p1).color should equal(piece.color)
  }

  it should "have its buffer of consumable events of size" in {
    gameMutableState.consumableBuffer.size should be(0)
  }

}
