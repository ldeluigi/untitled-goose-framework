package model.rules.behaviours

import mock.MatchMock
import model.events.persistent.LoseTurnEvent
import model.game.GameStateExtensions.MutableStateExtensions
import model.game.{Game, MutableGameState}
import model.rules.operations.Operation
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SkipTurnBehaviourTest extends AnyFlatSpec with Matchers {

  behavior of "SkipTurnBehaviourTest"

  it should "skip a turn and check if the game state has been altered correctly" in {
    val game: Game = MatchMock.default
    val event = LoseTurnEvent(game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)
    val state: MutableGameState = game.currentState

    state.submitEvent(event)

    val operationSequence: Seq[Operation] = SkipTurnBehaviour().applyRule(state)
    operationSequence.foreach(_.execute(state))

    // TODO check that a new persistent event LoseTurnEvent is now present
    pending
  }

}