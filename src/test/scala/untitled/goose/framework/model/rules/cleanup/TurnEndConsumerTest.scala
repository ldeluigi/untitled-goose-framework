package untitled.goose.framework.model.rules.cleanup

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.runtime.{Game, GameState}

class TurnEndConsumerTest extends AnyFlatSpec with Matchers {

  var gameMatch: Game = MatchMock.default
  val gameMutableState: GameState = gameMatch.currentState

  behavior of "TurnEndConsumerTest"

  it should "check if the current turn of the runtime state is correct and equal to zero" in {
    gameMutableState.currentTurn should equal(0)
  }

  it should "execute the correct procedure to end and consume a turn" in {
    TurnEndConsumer(gameMatch.definition.playerOrdering.next).applyRule(gameMutableState).currentTurn should not be equal(0)
  }

}
