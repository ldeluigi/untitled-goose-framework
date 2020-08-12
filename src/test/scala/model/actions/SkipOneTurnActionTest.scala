package model.actions

import engine.events.SkipTurnEvent
import engine.events.root.GameEvent
import mock.MatchMock.default
import org.scalatest.flatspec.AnyFlatSpec

class SkipOneTurnActionTest extends AnyFlatSpec {

  behavior of "SkipOneTurnActionTest"

  it should "fire proper event in execute" in {
    SkipOneTurnAction().execute((event: GameEvent) => assert(event.isInstanceOf[SkipTurnEvent]), default.currentState)
  }

}
