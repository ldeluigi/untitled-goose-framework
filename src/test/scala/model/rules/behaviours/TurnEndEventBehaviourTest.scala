package model.rules.behaviours

import mock.MatchMock
import model.events.consumable.{ConsumableGameEvent, TurnShouldEndEvent}
import model.events.persistent.{PersistentGameEvent, TurnEndedEvent}
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
  val turnEnded: PersistentGameEvent = TurnEndedEvent(game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)

  override protected def beforeEach(): Unit = {
    state.submitEvent(turnShouldEndEvent)
    val operationSequence: Seq[Operation] = TurnEndEventBehaviour().applyRule(state)
    operationSequence.foreach(_.execute(state))
  }

  it should "check if the turn has actually ended" in {
    pending
    game.currentState.currentPlayer.history should contain(turnEnded)
  }

  it should "still contains the main event since it's not a consumable one" in {
    pending
    game.currentState.currentPlayer.history should contain(turnShouldEndEvent)
  }

}