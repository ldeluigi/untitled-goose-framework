package model.rules.cleanup

import mock.MatchMock
import model.game.{Game, MutableGameState}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TurnEndConsumerTest extends AnyFlatSpec with Matchers {

  var gameMatch: Game = MatchMock.default
  val gameMutableState: MutableGameState = gameMatch.currentState

  behavior of "TurnEndConsumerTest"

  it should "check if the current turn of the game state is correct and equal to zero" in {
    gameMutableState.currentTurn should equal(0)
  }

  it should "execute the correct procedure to end and consume a turn" in {
    TurnEndConsumer.applyRule(gameMutableState) should not be equal(0)
  }

}
