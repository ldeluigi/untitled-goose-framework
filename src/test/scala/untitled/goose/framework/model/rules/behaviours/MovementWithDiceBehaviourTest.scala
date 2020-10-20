package untitled.goose.framework.model.rules.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.runtime.functional.GameStateExtensions.PimpedGameState
import untitled.goose.framework.model.entities.runtime.functional.GameStateUpdate.GameStateUpdateImpl
import untitled.goose.framework.model.entities.runtime.{Game, GameState}
import untitled.goose.framework.model.events.consumable._

class MovementWithDiceBehaviourTest extends AnyFlatSpec with Matchers {

  behavior of "MovementWithDiceBehaviourTest"

  val game: Game = MatchMock.default
  val state: GameState = game.currentState

  val diceResult: Int = 6

  val movementDiceRollEvent: ConsumableGameEvent = MovementDiceRollEvent(game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle, diceResult)
  val stepMovementEvent: ConsumableGameEvent = StepMovementEvent(diceResult, game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)


  it should "check that the right movement event has been submitted" in {
    val (s, ops) = MovementWithDiceBehaviour().applyRule(state.submitEvent(movementDiceRollEvent))
    ops.foldLeft(s)((s, r) => r.execute(s)).consumableBuffer should contain(stepMovementEvent)
  }

  it should "not contain the consumed movement event anymore" in {
    val (s, ops) = MovementWithDiceBehaviour().applyRule(state.submitEvent(movementDiceRollEvent))
    ops.foldLeft(s)((s, r) => r.execute(s)).consumableBuffer should not contain movementDiceRollEvent
  }

  it should "save the consumed event in the player's history" in {
    val (s, ops) = MovementWithDiceBehaviour().applyRule(state.submitEvent(movementDiceRollEvent))
    ops.foldLeft(s)((s, r) => r.execute(s)).currentPlayerInstance.history should contain(movementDiceRollEvent)
  }

}