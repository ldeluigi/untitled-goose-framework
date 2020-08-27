package model.rules.behaviours

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DialogLaunchBehaviourTest extends AnyFlatSpec with Matchers {

  behavior of "DialogLaunchBehaviourTest"

  /*it should "consume and convert Dialog Events into operations" in {
    val m: Game = MatchMock.default
    val e = DialogLaunchEvent(m.currentState.currentTurn, _ => DialogContent("", ""))
    m.submitEvent(e)
    DialogLaunchBehaviour().applyRule(m.currentState) should equal(Seq(DialogOperation(e.createDialog)))
    DialogLaunchBehaviour().applyRule(m.currentState) should have size 0
  }
  */
}