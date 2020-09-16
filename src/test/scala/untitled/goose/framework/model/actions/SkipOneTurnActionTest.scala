package untitled.goose.framework.model.actions

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock.default
import untitled.goose.framework.model.events.consumable.SkipTurnEvent

class SkipOneTurnActionTest extends AnyFlatSpec with Matchers {

  behavior of "SkipOneTurnActionTest"

  it should "fire proper event in execute" in {
    val event = SkipOneTurnAction().trigger(default.currentState)
    event.isInstanceOf[SkipTurnEvent] should be(true)
  }

}
