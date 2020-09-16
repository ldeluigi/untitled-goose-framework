package untitled.goose.framework.model.rules.operations

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.runtime.{Game, MutableGameState}
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.StopOnTileEvent

class OperationTest extends AnyFlatSpec with Matchers {

  var gameMatch: Game = MatchMock.default
  val gameMutableState: MutableGameState = gameMatch.currentState

  val gameEvent: GameEvent = StopOnTileEvent(gameMutableState.currentPlayer, gameMutableState.gameBoard.first, gameMutableState.currentTurn, gameMutableState.currentCycle)

  behavior of "OperationTest"

  it should "not return a trigger operation when the condition is false" in {
    Operation.triggerWhen(_ => false, _ => Seq(gameEvent)).execute(gameMutableState)
    gameMutableState.consumableBuffer should not contain gameEvent
  }

  it should "return an operation that update the runtime's state" in {
    var condition: Boolean = false
    Operation.updateState(_ => condition = true).execute(gameMutableState)
    condition should be(true)
  }

  it should "return a trigger operation when the condition is true" in {
    Operation.triggerWhen(_ => true, _ => Seq(gameEvent)).execute(gameMutableState)
    Operation.trigger(gameEvent)
    gameMutableState.consumableBuffer should contain(gameEvent)
  }

  it should "return an operation that trigger an event of the runtime's state" in {
    Operation.trigger(gameEvent).execute(gameMutableState)
    gameMutableState.consumableBuffer should contain(gameEvent)
  }

}
