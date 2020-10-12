package untitled.goose.framework.model.entities.runtime

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterEach, OneInstancePerTest}
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.Colour
import untitled.goose.framework.model.entities.runtime.functional.GameStateUpdate.GameStateUpdateImpl

class GameTest extends AnyFlatSpec with OneInstancePerTest with BeforeAndAfterEach with Matchers {

  var gameMatch: Game = MatchMock.default

  override def beforeEach(): Unit = {
    gameMatch = MatchMock.default
  }

  "A Match" should "have a MatchBoard" in {
    gameMatch.currentState.gameBoard.definition.tiles should equal(Board(MatchMock.board).definition.tiles)
  }

  it should "have a set of players" in {
    gameMatch.currentState.players should equal(MatchMock.players.keys.toSeq)
  }

  it should "have a current GameState" in {
    gameMatch.currentState should not be null
  }

  "A matchState" should "have a current player" in {
    val state = gameMatch.currentState

    state.currentPlayer should equal(MatchMock.p1)
  }

  it should "know where the playerPieces are" in {
    gameMatch.currentState.playerPieces(MatchMock.p1).position.isEmpty should be(true)
  }

  it should "update player pieces as told" in {
    gameMatch.currentState.updatePlayerPiece(MatchMock.p1, _ => Piece(Colour.Default.Blue, Some(Position(gameMatch.currentState.gameBoard.tileOrdering.first.definition)))).playerPieces(MatchMock.p1).position.get should equal(Position(gameMatch.currentState.gameBoard.tileOrdering.first.definition))
  }

}
