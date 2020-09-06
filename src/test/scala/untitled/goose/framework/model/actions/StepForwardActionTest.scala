package untitled.goose.framework.model.actions

import untitled.goose.framework.mock.MatchMock.default
import untitled.goose.framework.model.events.consumable.StepMovementEvent
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class StepForwardActionTest extends AnyFlatSpec with Matchers {

  behavior of "StepForwardActionTest"

  it should "execute a one step ahead action of exactly 1 step" in {
    val event = StepForwardAction().trigger(default.currentState)

    event.isInstanceOf[StepMovementEvent] && event.asInstanceOf[StepMovementEvent].movement == 1 should equal(true)
  }

}
