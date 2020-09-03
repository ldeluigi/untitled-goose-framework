package model.rules.behaviours

import mock.MatchMock
import model.entities.definitions.TileDefinition
import model.events.consumable.{ConsumableGameEvent, StopOnTileEvent, TeleportEvent, TileEnteredEvent}
import model.entities.runtime.GameStateExtensions.MutableStateExtensions
import model.entities.runtime.{Game, MutableGameState, Tile}
import model.rules.operations.Operation
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TeleportBehaviourTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  behavior of "TeleportBehaviourTest"

  val game: Game = MatchMock.default
  val state: MutableGameState = game.currentState

  val startingTile: Tile = Tile(TileDefinition(1))
  val landingTile: Tile = Tile(TileDefinition(7))

  val teleportEvent: ConsumableGameEvent = TeleportEvent(landingTile, game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)
  val stopOnTileEvent: ConsumableGameEvent = StopOnTileEvent(game.currentState.currentPlayer, landingTile, game.currentState.currentTurn, game.currentState.currentTurn)
  val tileEnteredEvent: ConsumableGameEvent = TileEnteredEvent(game.currentState.currentPlayer, landingTile, game.currentState.currentTurn, game.currentState.currentCycle)

  override protected def beforeEach(): Unit = {
    state.submitEvent(teleportEvent)
    val operationSequence: Seq[Operation] = TeleportBehaviour().applyRule(state)
    operationSequence.foreach(_.execute(state))
  }

  it should "check if a player has been teleported from a tile to another not immediately nearly-placed tile" in {
    state.consumableBuffer should contain(tileEnteredEvent)
  }

  it should "check if a player has stopped on the correct tile after being teleported" in {
    state.consumableBuffer should contain(stopOnTileEvent)
  }

  it should "not contain the consumed teleport event anymore" in {
    state.consumableBuffer should not contain teleportEvent
  }

}