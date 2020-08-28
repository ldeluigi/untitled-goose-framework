package model.rules.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DialogLaunchBehaviourTest extends AnyFlatSpec with Matchers {

  behavior of "DialogLaunchBehaviourTest"

  it should "consume and convert Dialog Events into operations" in {
    /*val game: Game = MatchMock.default
    val event = DialogLaunchEvent(game.currentState.currentTurn, game.currentState.currentCycle, DialogContent("title", "content"))
    val state: MutableGameState = game.currentState

    state.submitEvent(event)

    val operationSequence: Seq[Operation] = DialogLaunchBehaviour().applyRule(state)
    operationSequence.foreach(_.execute(state))

    state.consumableBuffer should contain theSameElementsAs operationSequence

    state.consumableBuffer should have size 1*/
    pending

  }

}