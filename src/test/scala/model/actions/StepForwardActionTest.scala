package model.actions

import engine.events.StepMovementEvent
import engine.events.root.GameEvent
import mock.MatchMock.default
import org.scalatest.flatspec.AnyFlatSpec

class StepForwardActionTest extends AnyFlatSpec {

  behavior of "StepForwardActionTest"

  it should "execute" in {
    StepForwardAction().execute((event: GameEvent) => assert(event.isInstanceOf[StepMovementEvent] && event.asInstanceOf[StepMovementEvent].movement == 1), default.currentState)
  }

}
