package untitled.goose.framework.model.rules.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.runtime.functional.GameStateExtensions.PimpedGameState
import untitled.goose.framework.model.entities.runtime.functional.GameStateUpdate.GameStateUpdateImpl
import untitled.goose.framework.model.entities.runtime.{Game, GameState}
import untitled.goose.framework.model.events.consumable.{ConsumableGameEvent, SkipTurnEvent}
import untitled.goose.framework.model.events.persistent.{LoseTurnEvent, PersistentGameEvent}

class SkipTurnBehaviourTest extends AnyFlatSpec with Matchers {

  behavior of "SkipTurnBehaviourTest"

  val game: Game = MatchMock.default
  val state: GameState = game.currentState

  val skipTurnEvent: ConsumableGameEvent = SkipTurnEvent(game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)
  val loseTurnEvent: PersistentGameEvent = LoseTurnEvent(game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)

  it should "check that a turn for the given player has been skipped" in {
    val (s, ops) = SkipTurnBehaviour().applyRule(state.submitEvent(loseTurnEvent).submitEvent(skipTurnEvent))
    ops.foldLeft(s)((s, r) => r.execute(s)).currentPlayerInstance.history should not contain loseTurnEvent
  }

  it should "not contain the consumed step event anymore" in {
    val (s, ops) = SkipTurnBehaviour().applyRule(state.submitEvent(loseTurnEvent).submitEvent(skipTurnEvent))
    ops.foldLeft(s)((s, r) => r.execute(s)).consumableBuffer should not contain skipTurnEvent
  }
}
