package untitled.goose.framework.model.rules.behaviours

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.definitions.TileDefinition
import untitled.goose.framework.model.entities.runtime.{Game, GameState, Tile}
import untitled.goose.framework.model.events.consumable._

class MovementWithDiceBehaviourTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  behavior of "MovementWithDiceBehaviourTest"

  val game: Game = MatchMock.default
  val state: GameState = game.currentState

  val diceResult: Int = 6
  val landingTile: Tile = Tile(TileDefinition(diceResult))

  val movementDiceRollEvent: ConsumableGameEvent = MovementDiceRollEvent(game.currentState.currentPlayer.definition, game.currentState.currentTurn, game.currentState.currentCycle, diceResult)
  val stepMovementEvent: ConsumableGameEvent = StepMovementEvent(diceResult, game.currentState.currentPlayer.definition, game.currentState.currentTurn, game.currentState.currentCycle)

  override protected def beforeEach(): Unit = {
    //state.submitEvent(movementDiceRollEvent)
    //val operationSequence: Seq[Operation] = MovementWithDiceBehaviour().applyRule(state)
    //operationSequence.foreach(_.execute(state))
  }

  it should "check that the right movement event has been submitted" in {
    //state.consumableBuffer should contain(stepMovementEvent)
    pending
  }

  it should "not contain the consumed movement event anymore" in {
    //state.consumableBuffer should not contain movementDiceRollEvent
    pending
  }

  it should "save the consumed event in the player's history" in {
    //state.currentPlayer.history should contain(movementDiceRollEvent)
    pending
  }

}