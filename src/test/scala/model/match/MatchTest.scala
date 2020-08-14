package model.`match`

import engine.events.{DiceRollEvent, TileEnteredEvent, TurnShouldEndEvent}
import mock.MatchMock
import mock.MatchMock._
import model.Color
import model.entities.board.{Piece, Position}
import model.rules.ruleset.{PlayerOrdering, PriorityRuleSet}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.{BeforeAndAfterEach, OneInstancePerTest}

class MatchTest extends AnyFlatSpec with OneInstancePerTest with BeforeAndAfterEach {

  var gameMatch: Match = MatchMock.default

  override def beforeEach(): Unit = {
    gameMatch = Match(board, players, PriorityRuleSet(playerOrdering = PlayerOrdering.givenOrder(Seq(p1, p2))))
  }

  "A Match" should "have a MatchBoard" in {
    assert(gameMatch.board.equals(MatchBoard(board)))
  }

  it should "have a set of players" in {
    assert(gameMatch.players.equals(players.keySet))
  }

  it should "have a current MatchState" in {
    assert(gameMatch.currentState != null)
  }

  it should "submit general events to the stateHistory" in {
    val gameEvent = TurnShouldEndEvent(gameMatch.currentState.currentTurn)
    gameMatch.submitEvent(gameEvent)
    assert(gameMatch.currentState.history.contains(gameEvent))
  }

  it should "submit events to players" in {
    val state = gameMatch.currentState
    val playerEvent = DiceRollEvent(state.currentPlayer, 1, state.currentTurn)
    gameMatch.submitEvent(playerEvent)

    assert(state.currentPlayer.history.contains(playerEvent))
  }

  it should "submit events to tiles" in {
    val state = gameMatch.currentState
    val tileEvent = TileEnteredEvent(state.currentPlayer, state.matchBoard.first, state.currentTurn)
    gameMatch.submitEvent(tileEvent)

    assert(state.matchBoard.first.history.contains(tileEvent))
  }

  "A matchState" should "have a current player" in {
    val state = gameMatch.currentState
    assert(state.currentPlayer.equals(p1))
  }

  it should "know who is the next player" in {
    val state = gameMatch.currentState
    assert(state.nextPlayer.equals(p2))
  }

  it should "know where the playerPieces are" in {
    assert(gameMatch.currentState.playerPieces(p1).position.isEmpty)
  }

  it should "update player pieces as told" in {
    gameMatch.currentState.updatePlayerPiece(p1, _ => Piece(Color.Blue, Some(Position(gameMatch.board.first))))
    assert(gameMatch.currentState.playerPieces(p1).position.get == Position(gameMatch.board.first))
  }

}
