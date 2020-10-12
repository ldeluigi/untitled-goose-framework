package untitled.goose.framework.model.entities.runtime.functional

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.runtime.functional.GameStateUpdate.GameStateUpdateImpl
import untitled.goose.framework.model.entities.runtime.functional.GameUpdate.GameUpdateImpl
import untitled.goose.framework.model.entities.runtime.{Game, PlayerDefinition}
import untitled.goose.framework.model.events.consumable.{ConsumableGameEvent, SkipTurnEvent}

class GameUpdateTest extends AnyFlatSpec with Matchers {

  behavior of "GameUpdateTest"

  val game: Game = MatchMock.default
  val p1: PlayerDefinition = MatchMock.p1
  val action: Action = MatchMock.action
  val consEvent: ConsumableGameEvent = SkipTurnEvent(p1,
    game.currentState.currentTurn,
    game.currentState.currentCycle)

  it should "have stateBasedOperations containing at least a turnShouldEndEvent trigger" in {
    game.stateBasedOperations._2.size should be(1)
  }

  it should "cleanup" in {
    val beforeCleanup = game
      .updateState(_.submitEvent(consEvent))
    val afterCleanup = beforeCleanup.cleanup.execute(beforeCleanup.currentState)
    afterCleanup.currentCycle should be > game.currentState.currentCycle
    afterCleanup.consumableBuffer should not contain consEvent
  }

  it should "updateState" in {
    game.updateState(_.saveEvent(consEvent)).currentState.players(p1).history should contain(consEvent)
  }

  it should "properly return availableActions" in {
    game.availableActions should equal(Set(MatchMock.action))
  }

}
