package model.actions

import mock.MatchMock.default
import model.events.consumable.SkipTurnEvent
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SkipOneTurnActionTest extends AnyFlatSpec with Matchers {

  behavior of "SkipOneTurnActionTest"

  it should "fire proper event in execute" in {
    val event = SkipOneTurnAction().trigger(default.currentState)
    event.isInstanceOf[SkipTurnEvent] should be(true)
  }

}
