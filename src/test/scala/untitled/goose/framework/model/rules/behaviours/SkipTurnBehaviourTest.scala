package untitled.goose.framework.model.rules.behaviours

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.entities.runtime.{Game, GameState}
import untitled.goose.framework.model.events.consumable.{ConsumableGameEvent, SkipTurnEvent}
import untitled.goose.framework.model.events.persistent.{LoseTurnEvent, PersistentGameEvent}

class SkipTurnBehaviourTest extends AnyFlatSpec with Matchers with BeforeAndAfterEach {

  behavior of "SkipTurnBehaviourTest"

  val game: Game = MatchMock.default
  val state: GameState = game.currentState

  val skipTurnEvent: ConsumableGameEvent = SkipTurnEvent(game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)
  val loseTurnEvent: PersistentGameEvent = LoseTurnEvent(game.currentState.currentPlayer, game.currentState.currentTurn, game.currentState.currentCycle)

  override protected def beforeEach(): Unit = {
    //state.submitEvent(loseTurnEvent)
    //state.submitEvent(skipTurnEvent)
    //val operationSequence: Seq[Operation] = SkipTurnBehaviour().applyRule(state)
    //operationSequence.foreach(_.execute(state))
  }

  it should "check that a turn for the given player has been skipped" in {
    //state.currentPlayer.history should not contain loseTurnEvent
    pending
  }

  it should "not contain the consumed step event anymore" in {
    //state.consumableBuffer should not contain skipTurnEvent
    pending
  }
}
