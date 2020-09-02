package model.game

import mock.MatchMock
import model.Color
import model.entities.board.{Piece, Position}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterEach, OneInstancePerTest}

class GameTest extends AnyFlatSpec with OneInstancePerTest with BeforeAndAfterEach with Matchers {

  var gameMatch: Game = MatchMock.default

  override def beforeEach(): Unit = {
    gameMatch = MatchMock.default
  }

  "A Match" should "have a MatchBoard" in {
    gameMatch.board.board.tiles should equal(GameBoard(MatchMock.board).board.tiles)
  }

  it should "have a set of players" in {
    gameMatch.currentState.players should equal(MatchMock.players.keySet)
  }

  it should "have a current GameState" in {
    gameMatch.currentState should not be null
  }

  "A matchState" should "have a current player" in {
    val state = gameMatch.currentState

    state.currentPlayer should equal(MatchMock.p1)
  }

  it should "know who is the next player" in {
    val state = gameMatch.currentState
    state.nextPlayer should equal(MatchMock.p2)
  }

  it should "know where the playerPieces are" in {
    gameMatch.currentState.playerPieces(MatchMock.p1).position.isEmpty should be(true)
  }

  it should "update player pieces as told" in {
    gameMatch.currentState.updatePlayerPiece(MatchMock.p1, _ => Piece(Color.Blue, Some(Position(gameMatch.board.first))))

    gameMatch.currentState.playerPieces(MatchMock.p1).position.get should equal(Position(gameMatch.board.first))
  }

}
