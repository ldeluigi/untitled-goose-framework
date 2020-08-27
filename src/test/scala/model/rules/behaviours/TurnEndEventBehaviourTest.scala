package model.rules.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TurnEndEventBehaviourTest extends AnyFlatSpec with Matchers {

  behavior of "TurnEndEventBehaviourTest"

  /*it should "check for TurnShouldEndEvent for the current turn and if not found fire it" in {
    val m: Game = MatchMock.default
    val e = TurnShouldEndEvent(m.currentState.currentTurn)
    val ops: Seq[Operation] = TurnEndEventBehaviour().applyRule(m.currentState)
    ops should have size 1
    m.submitEvent(e)
    TurnEndEventBehaviour().applyRule(m.currentState) should have size 0
    ops.head.execute(m.currentState, ev => {
      ev should equal(TurnShouldEndEvent(m.currentState.currentTurn))
    })
  }
  */
}