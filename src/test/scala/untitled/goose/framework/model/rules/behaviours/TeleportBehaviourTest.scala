package untitled.goose.framework.model.rules.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.definitions.TileDefinition
import untitled.goose.framework.model.entities.runtime.functional.GameStateUpdate.GameStateUpdateImpl
import untitled.goose.framework.model.entities.runtime.{Game, GameState, Tile}
import untitled.goose.framework.model.events.consumable.{ConsumableGameEvent, StopOnTileEvent, TeleportEvent, TileEnteredEvent}

class TeleportBehaviourTest extends AnyFlatSpec with Matchers {

  behavior of "TeleportBehaviourTest"

  val game: Game = MatchMock.default
  val state: GameState = game.currentState

  val landingTile: Tile = Tile(TileDefinition(7))

  val teleportEvent: ConsumableGameEvent = TeleportEvent(landingTile.definition, game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)
  val stopOnTileEvent: ConsumableGameEvent = StopOnTileEvent(game.currentState.currentPlayer, landingTile.definition, game.currentState.currentTurn, game.currentState.currentTurn)
  val tileEnteredEvent: ConsumableGameEvent = TileEnteredEvent(game.currentState.currentPlayer, landingTile.definition, game.currentState.currentTurn, game.currentState.currentCycle)

  it should "check if a player has been teleported from a tile to another not immediately nearly-placed tile" in {
    val (s, ops) = TeleportBehaviour().applyRule(state.submitEvent(teleportEvent))
    ops.foldLeft(s)((s, r) => r.execute(s)).consumableBuffer should contain(tileEnteredEvent)
  }

  it should "check if a player has stopped on the correct tile after being teleported" in {
    val (s, ops) = TeleportBehaviour().applyRule(state.submitEvent(teleportEvent))
    ops.foldLeft(s)((s, r) => r.execute(s)).consumableBuffer should contain(stopOnTileEvent)
  }

  it should "not contain the consumed teleport event anymore" in {
    val (s, ops) = TeleportBehaviour().applyRule(state.submitEvent(teleportEvent))
    ops.foldLeft(s)((s, r) => r.execute(s)).consumableBuffer should not contain teleportEvent
  }

}