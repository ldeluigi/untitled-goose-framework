package untitled.goose.framework.model.actions

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock.default
import untitled.goose.framework.model.events.consumable.StepMovementEvent

class StepForwardActionTest extends AnyFlatSpec with Matchers {

  behavior of "StepForwardActionTest"

  it should "execute a one step ahead action of exactly 1 step" in {
    val event = StepForwardAction().trigger(default.currentState)

    event.isInstanceOf[StepMovementEvent] should be(true)
    event.asInstanceOf[StepMovementEvent].movement should be
    1
  }

}
