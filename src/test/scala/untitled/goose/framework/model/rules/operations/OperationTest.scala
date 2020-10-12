package untitled.goose.framework.model.rules.operations

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.runtime.{Game, GameState}
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.StopOnTileEvent

class OperationTest extends AnyFlatSpec with Matchers {

  var gameMatch: Game = MatchMock.default
  val gameMutableState: GameState = gameMatch.currentState

  val gameEvent: GameEvent = StopOnTileEvent(gameMutableState.currentPlayer.definition, gameMutableState.gameBoard.tileOrdering.first.definition, gameMutableState.currentTurn, gameMutableState.currentCycle)

  behavior of "OperationTest"

  it should "not return a trigger operation when the condition is false" in {
    Operation.triggerWhen(_ => false, _ => Seq(gameEvent)).execute(gameMutableState).consumableBuffer should not contain gameEvent
  }

  it should "return an operation that updates the state" in {
    var condition: Boolean = false
    Operation.updateState(s => {
      condition = true
      s
    }).execute(gameMutableState)
    condition should be(true)
  }

  it should "return a trigger operation when the condition is true" in {
    Operation.triggerWhen(_ => true, _ => Seq(gameEvent)).execute(gameMutableState).consumableBuffer should contain(gameEvent)
  }

  it should "return an operation that triggers an event on the state" in {
    Operation.trigger(gameEvent).execute(gameMutableState).consumableBuffer should contain(gameEvent)
  }

}
