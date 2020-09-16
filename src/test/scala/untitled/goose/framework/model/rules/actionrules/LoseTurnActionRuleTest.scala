package untitled.goose.framework.model.rules.actionrules

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.actions.StepForwardAction

class LoseTurnActionRuleTest extends AnyFlatSpec with Matchers {

  val emptyLoseTurnActionRule: LoseTurnActionRule = LoseTurnActionRule(Set(), 1)

  val loseTurnActionRule: LoseTurnActionRule = LoseTurnActionRule(Set(StepForwardAction()), 1)

  behavior of "LoseTurnActionRuleTest"

  it should "do nothing without event" in {
    loseTurnActionRule.allowedActions(MatchMock.default.currentState).isEmpty should be(true)
  }

}
