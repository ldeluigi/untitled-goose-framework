package model.rules.behaviours

import engine.events.{MovementDiceRollEvent, StepMovementEvent}
import mock.MatchMock
import model.game.Game
import model.rules.operations.Operation
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MovementWithDiceBehaviourTest extends AnyFlatSpec with Matchers {

  behavior of "MovementWithDiceBehaviourTest"

  it should "consume input event and trigger the right output event afterwards" in {
    val m: Game = MatchMock.default
    val e = MovementDiceRollEvent(m.currentState.currentPlayer, m.currentState.currentTurn, 6)
    m.submitEvent(e)
    val ops: Seq[Operation] = MovementWithDiceBehaviour().applyRule(m.currentState)
    ops should have size 1
    MovementWithDiceBehaviour().applyRule(m.currentState) should have size 0
    ops.head.execute(m.currentState, ev => {
      ev should equal(StepMovementEvent(6, e.source, m.currentState.currentTurn))
    })

  }

}
