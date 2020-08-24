package model.actions

import engine.events.GameEvent
import engine.events.consumable.StepMovementEvent
import mock.MatchMock.default
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class StepForwardActionTest extends AnyFlatSpec with Matchers {

  behavior of "StepForwardActionTest"

  it should "execute" in {
    StepForwardAction().execute((event: GameEvent) =>
      event.isInstanceOf[StepMovementEvent] && event.asInstanceOf[StepMovementEvent].movement == 1 should equal(true)
      , default.currentState)
  }

}
