package untitled.goose.framework.model.rules.actionrules

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.mock.MatchMock
import untitled.goose.framework.model.actions.StepForwardAction
import untitled.goose.framework.model.rules.actionrules.AlwaysActionRule.{AlwaysNegatedActionRule, AlwaysPermittedActionRule}

class AlwaysActionRuleTest extends AnyFlatSpec with Matchers {

  "AlwaysPermittedActionRule" should "always permit given actions with given priority" in {
    val a = AlwaysPermittedActionRule(5, StepForwardAction())
    a.allowedActions(MatchMock.default.currentState) should equal(Set(ActionAvailability(StepForwardAction(), 5)))
  }

  "AlwaysNegatedActionRule" should "never permit given actions with given priority" in {
    val a = AlwaysNegatedActionRule(5, StepForwardAction())
    a.allowedActions(MatchMock.default.currentState) should contain theSameElementsAs Set(ActionAvailability(StepForwardAction(), 5, allowed = false))
  }
}
