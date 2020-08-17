package model.game

import engine.events.{DiceRollEvent, TileEnteredEvent, TurnShouldEndEvent}
import mock.MatchMock
import mock.MatchMock._
import model.Color
import model.entities.board.{Piece, Position}
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{BeforeAndAfterEach, OneInstancePerTest}

class GameTest extends AnyFlatSpec with OneInstancePerTest with BeforeAndAfterEach with Matchers {

  var gameMatch: Game = MatchMock.default

  override def beforeEach(): Unit = {
    gameMatch = Game(board, players, PriorityRuleSet(playerOrdering = PlayerOrdering.givenOrder(Seq(p1, p2))))
  }

  "A Match" should "have a MatchBoard" in {
    gameMatch.board should equal(GameBoard(board))
  }

  it should "have a set of players" in {
    gameMatch.currentState.players should equal(players.keySet)
  }

  it should "have a current GameState" in {
    gameMatch.currentState should not be null
  }

  it should "submit general events to the stateHistory" in {
    val gameEvent = TurnShouldEndEvent(gameMatch.currentState.currentTurn)
    gameMatch.submitEvent(gameEvent)

    gameMatch.currentState.history should contain(gameEvent)
  }

  it should "submit events to players" in {
    val state = gameMatch.currentState
    val playerEvent = DiceRollEvent(state.currentPlayer, 1, state.currentTurn)
    gameMatch.submitEvent(playerEvent)

    state.currentPlayer.history should contain(playerEvent)
  }

  it should "submit events to tiles" in {
    val state = gameMatch.currentState
    val tileEvent = TileEnteredEvent(state.currentPlayer, state.gameBoard.first, state.currentTurn)
    gameMatch.submitEvent(tileEvent)

    state.gameBoard.first.history should contain(tileEvent)
  }

  "A matchState" should "have a current player" in {
    val state = gameMatch.currentState

    state.currentPlayer should equal(p1)
  }

  it should "know who is the next player" in {
    val state = gameMatch.currentState
    state.nextPlayer should equal(p2)
  }

  it should "know where the playerPieces are" in {
    gameMatch.currentState.playerPieces(p1).position.isEmpty should be(true)
  }

  it should "update player pieces as told" in {
    gameMatch.currentState.updatePlayerPiece(p1, _ => Piece(Color.Blue, Some(Position(gameMatch.board.first))))

    gameMatch.currentState.playerPieces(p1).position.get should equal(Position(gameMatch.board.first))
  }

}
