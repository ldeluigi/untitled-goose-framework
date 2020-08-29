package model.rules.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MultipleStepBehaviourTest extends AnyFlatSpec with Matchers {

  behavior of "MultipleStepBehaviourTest"

  it should "execute multiple steps and check if the game state has been altered correctly" in {
    /*val game: Game = MatchMock.default
    val event = StepMovementEvent(1, game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)
    val state: MutableGameState = game.currentState

    state.submitEvent(event)

    val operationSequence: Seq[Operation] = MultipleStepBehaviour().applyRule(state)
    operationSequence.foreach(_.execute(state))

    state.consumableBuffer should contain theSameElementsAs operationSequence

    // TODO check if player has moved by 1 tile and that it contains TileEnteredEvent and StopOnTileEvent*/
    pending

  }
}