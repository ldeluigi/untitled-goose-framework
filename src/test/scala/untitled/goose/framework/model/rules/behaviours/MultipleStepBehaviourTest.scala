package untitled.goose.framework.model.rules.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.definitions.TileDefinition
import untitled.goose.framework.model.entities.runtime.functional.GameStateUpdate.GameStateUpdateImpl
import untitled.goose.framework.model.entities.runtime.{Game, GameState, Tile}
import untitled.goose.framework.model.events.consumable._

class MultipleStepBehaviourTest extends AnyFlatSpec with Matchers {

  behavior of "MultipleStepBehaviourTest"

  val game: Game = MatchMock.default
  val state: GameState = game.currentState

  val landingTile: Tile = Tile(TileDefinition(5))

  val movementEvent: ConsumableGameEvent = StepMovementEvent(5, game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)
  val stopOnTileEvent: ConsumableGameEvent = StopOnTileEvent(game.currentState.currentPlayer, landingTile.definition, game.currentState.currentTurn, game.currentState.currentTurn)
  val tileEnteredEvent: ConsumableGameEvent = TileEnteredEvent(game.currentState.currentPlayer, landingTile.definition, game.currentState.currentTurn, game.currentState.currentCycle)
  val tileLeftEvent: ConsumableGameEvent = TileExitedEvent(game.currentState.currentPlayer, Tile(TileDefinition(1)).definition, game.currentState.currentTurn, game.currentState.currentCycle)

  it should "check that the given player has stopped on the intended tile" in {
    val (s, ops) = MultipleStepBehaviour().applyRule(state.submitEvent(movementEvent))
    ops.foldLeft(s)((s, r) => r.execute(s)).consumableBuffer should contain(stopOnTileEvent)
  }

  it should "check that the given player has left the supposed tile" in {
    val (s, ops) = MultipleStepBehaviour().applyRule(state.submitEvent(movementEvent))
    ops.foldLeft(s)((s, r) => r.execute(s)).consumableBuffer should contain(tileLeftEvent)
  }

  it should "check that the given player has entered the supposed tile" in {
    val (s, ops) = MultipleStepBehaviour().applyRule(state.submitEvent(movementEvent))
    ops.foldLeft(s)((s, r) => r.execute(s)).consumableBuffer should contain(tileEnteredEvent)
  }

  it should "not contain the consumed step event anymore" in {
    val (s, ops) = MultipleStepBehaviour().applyRule(state.submitEvent(movementEvent))
    ops.foldLeft(s)((s, r) => r.execute(s)).consumableBuffer should not contain movementEvent
  }

}