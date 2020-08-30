package model.rules.behaviours

import engine.events.consumable.{ConsumableGameEvent, TeleportEvent, TileEnteredEvent}
import mock.MatchMock
import model.Tile
import model.entities.board.TileDefinition
import model.game.GameStateExtensions.MutableStateExtensions
import model.game.{Game, MutableGameState}
import model.rules.operations.Operation
import org.scalatest.flatspec.AnyFlatSpec

class TeleportBehaviourTest extends AnyFlatSpec {

  val startingTile: Tile = Tile(TileDefinition(1))
  val tileToBeTeleportedAt: Tile = Tile(TileDefinition(15))

  behavior of "TeleportBehaviourTest"

  it should "teleport a player from a tile to another and check if the game state has been altered correctly" in {
    val game: Game = MatchMock.default
    val event = TeleportEvent(startingTile, game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)
    val state: MutableGameState = game.currentState
    val tileEnteredEvent: ConsumableGameEvent = TileEnteredEvent(game.currentState.currentPlayer, tileToBeTeleportedAt,
      game.currentState.currentTurn, game.currentState.currentCycle)

    state.submitEvent(event)

    val operationSequence: Seq[Operation] = MultipleStepBehaviour().applyRule(state)
    operationSequence.foreach(_.execute(state))

    // TODO check why operationsSequence stays empty and if player has moved to the specified tile and that it contains TileEnteredEvent
    //state.consumableBuffer should contain theSameElementsAs operationSequence

    pending
  }

}