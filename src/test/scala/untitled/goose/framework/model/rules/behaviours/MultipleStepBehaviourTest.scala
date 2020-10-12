package untitled.goose.framework.model.rules.behaviours

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.definitions.TileDefinition
import untitled.goose.framework.model.entities.runtime.{Game, GameState, Tile}
import untitled.goose.framework.model.events.consumable._

class MultipleStepBehaviourTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  behavior of "MultipleStepBehaviourTest"

  val game: Game = MatchMock.default
  val state: GameState = game.currentState

  val landingTile: Tile = Tile(TileDefinition(5))

  val movementEvent: ConsumableGameEvent = StepMovementEvent(5, game.currentState.currentPlayer.definition, game.currentState.currentTurn, game.currentState.currentCycle)
  val stopOnTileEvent: ConsumableGameEvent = StopOnTileEvent(game.currentState.currentPlayer.definition, landingTile.definition, game.currentState.currentTurn, game.currentState.currentTurn)
  val tileEnteredEvent: ConsumableGameEvent = TileEnteredEvent(game.currentState.currentPlayer.definition, landingTile.definition, game.currentState.currentTurn, game.currentState.currentCycle)
  val tileLeftEvent: ConsumableGameEvent = TileExitedEvent(game.currentState.currentPlayer.definition, Tile(TileDefinition(1)).definition, game.currentState.currentTurn, game.currentState.currentCycle)

  override protected def beforeEach(): Unit = {
    //  state.submitEvent(movementEvent)
    //  val operationSequence: Seq[Operation] = MultipleStepBehaviour().applyRule(state)
    //  operationSequence.foreach(_.execute(state))
  }

  it should "check that the given player has stopped on the intended tile" in {
    //state.consumableBuffer should contain(stopOnTileEvent)
    pending
  }

  it should "check that the given player has left the supposed tile" in {
    //state.consumableBuffer should contain(tileLeftEvent)
    pending
  }

  it should "check that the given player has entered the supposed tile" in {
    //state.consumableBuffer should contain(tileEnteredEvent)
    pending
  }

  it should "not contain the consumed step event anymore" in {
    //state.consumableBuffer should not contain movementEvent
    pending
  }

}