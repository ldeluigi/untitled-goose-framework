package model.actions

import engine.events.consumable.SkipTurnEvent
import mock.MatchMock.default
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SkipOneTurnActionTest extends AnyFlatSpec with Matchers {

  behavior of "SkipOneTurnActionTest"

  it should "fire proper event in execute" in {
    val event = SkipOneTurnAction().trigger(default.currentState)
    event.isInstanceOf[SkipTurnEvent] should be(true)
  }

}
