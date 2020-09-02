package model.rules.behaviours

import mock.MatchMock
import model.events.consumable.{ConsumableGameEvent, TurnShouldEndEvent}
import model.game.GameStateExtensions.MutableStateExtensions
import model.game.{Game, MutableGameState}
import model.rules.operations.Operation
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TurnEndEventBehaviourTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  behavior of "TurnEndEventBehaviourTest"

  val game: Game = MatchMock.default
  val state: MutableGameState = game.currentState

  val turnShouldEndEvent: ConsumableGameEvent = TurnShouldEndEvent(game.currentState.currentTurn, game.currentState.currentCycle)

  it should "still contains the main event since it's not a consumable one" in {
    val operationSequence: Seq[Operation] = TurnEndEventBehaviour().applyRule(state)
    operationSequence.foreach(_.execute(state))
    state.consumableBuffer should contain(turnShouldEndEvent)
  }

  it should "" in {
    state.submitEvent(turnShouldEndEvent)
    val operationSequence: Seq[Operation] = TurnEndEventBehaviour().applyRule(state)
    operationSequence.foreach(_.execute(state))
    state.consumableBuffer.size should be(1)
  }

}