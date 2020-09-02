package model.rules.behaviours

import mock.MatchMock
import model.entities.board.TileDefinition
import model.events.consumable._
import model.game.GameStateExtensions.MutableStateExtensions
import model.game.{Game, MutableGameState, Tile}
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

  override protected def beforeEach(): Unit = {
    state.submitEvent(movementDiceRollEvent)
    val operationSequence: Seq[Operation] = MovementWithDiceBehaviour().applyRule(state)
    operationSequence.foreach(_.execute(state))
  }

  it should "check that the right movement event has been submitted" in {
    state.consumableBuffer should contain(stepMovementEvent)
  }

  it should "not contain the consumed movement event anymore" in {
    state.consumableBuffer should not contain movementDiceRollEvent
  }

  it should "save the consumed event in the player's history" in {
    state.currentPlayer.history should contain(movementDiceRollEvent)
  }

}