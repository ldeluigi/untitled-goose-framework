package model.rules.behaviours

import mock.MatchMock
import model.events.consumable.{ConsumableGameEvent, TurnShouldEndEvent}
import model.entities.runtime.GameStateExtensions.MutableStateExtensions
import model.entities.runtime.{Game, MutableGameState}
import model.rules.operations.Operation
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TurnEndEventBehaviourTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  behavior of "TurnEndEventBehaviourTest"

  var game: Game = MatchMock.default
  var state: MutableGameState = game.currentState

  val turnShouldEndEvent: ConsumableGameEvent = TurnShouldEndEvent(game.currentState.currentTurn, game.currentState.currentCycle)

  override protected def beforeEach(): Unit = {
    game = MatchMock.default
    state = game.currentState
  }

  it should "still contain the main event since it's not a consumable one" in {
    val operationSequence: Seq[Operation] = TurnEndEventBehaviour().applyRule(state)
    operationSequence.foreach(_.execute(state))
    state.consumableBuffer should contain(turnShouldEndEvent)
  }

  it should "submit the event and check for its existence" in {
    state.submitEvent(turnShouldEndEvent)
    val operationSequence: Seq[Operation] = TurnEndEventBehaviour().applyRule(state)
    operationSequence.foreach(_.execute(state))
    state.consumableBuffer.size should be(1)
  }

}