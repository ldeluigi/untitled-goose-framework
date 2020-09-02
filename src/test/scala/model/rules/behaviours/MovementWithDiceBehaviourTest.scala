package model.rules.behaviours

import mock.MatchMock
import model.Tile
import model.entities.board.TileDefinition
import model.events.consumable._
import model.game.GameStateExtensions.MutableStateExtensions
import model.game.{Game, MutableGameState}
import model.rules.operations.Operation
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MovementWithDiceBehaviourTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  behavior of "MovementWithDiceBehaviourTest"

  val game: Game = MatchMock.default
  val state: MutableGameState = game.currentState

  val diceResult: Int = 6
  val landingTile: Tile = Tile(TileDefinition(diceResult))

  val movementDiceRollEvent: ConsumableGameEvent = MovementDiceRollEvent(game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle, diceResult)
  val stepMovementEvent: ConsumableGameEvent = StepMovementEvent(diceResult, game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)
  val stopOnTileEvent: ConsumableGameEvent = StopOnTileEvent(game.currentState.currentPlayer, landingTile, game.currentState.currentTurn, game.currentState.currentTurn)
  val tileEnteredEvent: ConsumableGameEvent = TileEnteredEvent(game.currentState.currentPlayer, landingTile, game.currentState.currentTurn, game.currentState.currentCycle)
  val tileLeftEvent: ConsumableGameEvent = TileExitedEvent(game.currentState.currentPlayer, Tile(TileDefinition(1)), game.currentState.currentTurn, game.currentState.currentCycle)

  override protected def beforeEach(): Unit = {
    state.submitEvent(movementDiceRollEvent)
    val operationSequence: Seq[Operation] = MovementWithDiceBehaviour().applyRule(state)
    operationSequence.foreach(_.execute(state))
  }

  it should "check that the given player has moved by n steps" in {
    println(state.consumableBuffer)
    state.consumableBuffer should contain(stepMovementEvent)
  }

  it should "check that the given player has stopped on the intended tile" in {
    state.consumableBuffer should contain(stopOnTileEvent)
  }

  it should "check that the given player has left the supposed tile" in {
    pending
    state.consumableBuffer should contain(tileLeftEvent)
  }

  it should "check that the given player has entered the supposed tile" in {
    pending
    state.consumableBuffer should contain(tileEnteredEvent)
  }

  it should "not contain the consumed movement event anymore" in {
    pending
    state.consumableBuffer should not contain movementDiceRollEvent
  }

}