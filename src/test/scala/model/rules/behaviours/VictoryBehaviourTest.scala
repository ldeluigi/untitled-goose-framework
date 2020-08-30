package model.rules.behaviours

import engine.events.consumable.VictoryEvent
import mock.MatchMock
import model.game.GameStateExtensions.MutableStateExtensions
import model.game.{Game, MutableGameState}
import model.rules.operations.Operation
import org.scalatest.flatspec.AnyFlatSpec

class VictoryBehaviourTest extends AnyFlatSpec {

  behavior of "VictoryBehaviourTest"

  it should "look for players with victory events, consume and launch dialog if found at least one" in {
    val game: Game = MatchMock.default
    val event = VictoryEvent(game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)
    val state: MutableGameState = game.currentState

    state.submitEvent(event)

    val operationSequence: Seq[Operation] = VictoryBehaviour().applyRule(state)
    operationSequence.foreach(_.execute(state))

    // TODO check why operationsSequence stays empty and check for player that won the game
    //state.consumableBuffer should contain theSameElementsAs operationSequence

    pending

  }

}
