package model.rules.behaviours

import engine.events.consumable.TurnShouldEndEvent
import mock.MatchMock
import model.game.GameStateExtensions.MutableStateExtensions
import model.game.{Game, MutableGameState}
import model.rules.operations.Operation
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TurnEndEventBehaviourTest extends AnyFlatSpec with Matchers {

  behavior of "TurnEndEventBehaviourTest"

  it should "check for TurnShouldEndEvent for the current turn and if not found fire it" in {
    val game: Game = MatchMock.default
    val event = TurnShouldEndEvent(game.currentState.currentTurn, game.currentState.currentCycle)
    val state: MutableGameState = game.currentState

    state.submitEvent(event)

    val operationSequence: Seq[Operation] = TurnEndEventBehaviour().applyRule(state)
    operationSequence.foreach(_.execute(state))

    // TODO check that a new persistent event TurnEndEventBehaviour is now present
    pending
  }

}