package untitled.goose.framework.model.rules.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.runtime.functional.GameStateUpdate.GameStateUpdateImpl
import untitled.goose.framework.model.entities.runtime.{Game, GameState}
import untitled.goose.framework.model.events.consumable.{ConsumableGameEvent, TurnShouldEndEvent}

class TurnEndEventBehaviourTest extends AnyFlatSpec with Matchers {

  behavior of "TurnEndEventBehaviourTest"

  val game: Game = MatchMock.default
  val state: GameState = game.currentState

  val turnShouldEndEvent: ConsumableGameEvent = TurnShouldEndEvent(game.currentState.currentTurn, game.currentState.currentCycle)

  it should "still contain the untitled.goose.framework.main event since it's not a consumable one" in {
    val (s, ops) = TurnEndEventBehaviour().applyRule(state)
    ops.foldLeft(s)((s, r) => r.execute(s)).consumableBuffer should contain(turnShouldEndEvent)
  }

  it should "submit the event and check for its existence" in {
    val (s, ops) = TurnEndEventBehaviour().applyRule(state.submitEvent(turnShouldEndEvent))
    ops.foldLeft(s)((s, r) => r.execute(s)).consumableBuffer.size should be(1)
  }

}