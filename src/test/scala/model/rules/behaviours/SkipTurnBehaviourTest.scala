package model.rules.behaviours

import mock.MatchMock
import model.events.consumable.{ConsumableGameEvent, SkipTurnEvent}
import model.events.persistent.{LoseTurnEvent, PersistentGameEvent}
import model.game.GameStateExtensions.MutableStateExtensions
import model.game.{Game, MutableGameState}
import model.rules.operations.Operation
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SkipTurnBehaviourTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  behavior of "SkipTurnBehaviourTest"

  val game: Game = MatchMock.default
  val state: MutableGameState = game.currentState

  val skipTurnEvent: ConsumableGameEvent = SkipTurnEvent(game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)
  val loseTurnEvent: PersistentGameEvent = LoseTurnEvent(game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)

  override protected def beforeEach(): Unit = {
    state.submitEvent(skipTurnEvent)
    val operationSequence: Seq[Operation] = SkipTurnBehaviour().applyRule(state)
    operationSequence.foreach(_.execute(state))
  }

  it should "check that a turn for the given player has been skipped" in {
    pending
    game.currentState.currentPlayer.history should contain(loseTurnEvent)
  }

  it should "not contain the consumed step event anymore" in {
    state.consumableBuffer should not contain skipTurnEvent
  }

}