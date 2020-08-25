package model.actions

import engine.events.GameEvent
import engine.events.consumable.SkipTurnEvent
import mock.MatchMock.default
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SkipOneTurnActionTest extends AnyFlatSpec with Matchers {

  behavior of "SkipOneTurnActionTest"

  it should "fire proper event in execute" in {
    SkipOneTurnAction().execute((event: GameEvent) =>
      event.isInstanceOf[SkipTurnEvent] should be(true)
      , default.currentState)
  }

}
