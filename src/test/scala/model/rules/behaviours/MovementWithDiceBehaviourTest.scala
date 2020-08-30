package model.rules.behaviours

import engine.events.consumable.MovementDiceRollEvent
import mock.MatchMock
import model.game.GameStateExtensions.MutableStateExtensions
import model.game.{Game, MutableGameState}
import model.rules.operations.Operation
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MovementWithDiceBehaviourTest extends AnyFlatSpec with Matchers {

  behavior of "MovementWithDiceBehaviourTest"

  it should "consume input event and trigger the right output event afterwards" in {
    val game: Game = MatchMock.default
    val event = MovementDiceRollEvent(game.currentState.currentPlayer, game.currentState.currentTurn, 6)
    val state: MutableGameState = game.currentState

    state.submitEvent(event)

    val operationSequence: Seq[Operation] = MovementWithDiceBehaviour().applyRule(state) // restituisce sequenze di StepMovementEvent
    operationSequence.foreach(_.execute(state))

    //state.consumableBuffer should contain theSameElementsAs operationSequence
    //operationSequence should have size 1
    //operationSequence should equal(StepMovementEvent(6, game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle))
  }

}