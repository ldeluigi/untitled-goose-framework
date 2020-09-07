package untitled.goose.framework.model.rules.operations

import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.StopOnTileEvent
import untitled.goose.framework.model.entities.runtime.{Game, MutableGameState}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class OperationTest extends AnyFlatSpec with Matchers {

  var gameMatch: Game = MatchMock.default
  val gameMutableState: MutableGameState = gameMatch.currentState

  val gameEvent: GameEvent = StopOnTileEvent(gameMutableState.currentPlayer, gameMutableState.gameBoard.first, gameMutableState.currentTurn, gameMutableState.currentCycle)

  behavior of "OperationTest"

  it should "return an operation that update the runtime's state" in {
    // TODO remove the assert (never use assert!)
    Operation.updateState(_ => assert(true)).execute(gameMutableState)
  }

  it should "return a trigger operation when the condition is true" in {
    Operation.triggerWhen(_ => true, _ => Seq(gameEvent)).execute(gameMutableState)
    Operation.trigger(gameEvent)
  }

  it should "not return a trigger operation when the condition is false" in {
    Operation.triggerWhen(_ => false, _ => Seq(gameEvent)).execute(gameMutableState)
  }

  it should "return an operation that trigger an event of the runtime's state" in {
    Operation.trigger(gameEvent).execute(gameMutableState)
    gameMutableState.consumableBuffer should contain(gameEvent)
  }

}
