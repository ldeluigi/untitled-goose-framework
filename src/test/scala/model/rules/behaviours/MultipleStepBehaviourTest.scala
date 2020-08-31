package model.rules.behaviours

import engine.events.consumable.{ConsumableGameEvent, StepMovementEvent, StopOnTileEvent, TileEnteredEvent}
import mock.MatchMock
import model.Tile
import model.entities.board.TileDefinition
import model.game.GameStateExtensions.MutableStateExtensions
import model.game.{Game, MutableGameState}
import model.rules.operations.Operation
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MultipleStepBehaviourTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  behavior of "MultipleStepBehaviourTest"

  val game: Game = MatchMock.default
  val event: ConsumableGameEvent = StepMovementEvent(1, game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)
  val stopOnTileEvent: ConsumableGameEvent = StopOnTileEvent(game.currentState.currentPlayer, Tile(TileDefinition(1)), game.currentState.currentTurn, game.currentState.currentTurn)
  val tileEnteredEvent: ConsumableGameEvent = TileEnteredEvent(game.currentState.currentPlayer, Tile(TileDefinition(1)), game.currentState.currentTurn, game.currentState.currentCycle)
  val state: MutableGameState = game.currentState


  override protected def beforeEach(): Unit = {
    state.submitEvent(event)
    val operationSequence: Seq[Operation] = MultipleStepBehaviour().applyRule(state)
    operationSequence.foreach(_.execute(state))
  }

  it should "check that the given player has stopped on the intended tile" in {
    state.consumableBuffer should contain(stopOnTileEvent)
  }

  it should "check that the given player has entered the supposed tile" in {
    state.consumableBuffer should contain(tileEnteredEvent)
  }

}