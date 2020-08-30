package model.rules.actionrules

import mock.MatchMock
import model.actions.StepForwardAction
import model.rules.actionrules.AlwaysActionRule.{AlwaysNegatedActionRule, AlwaysPermittedActionRule}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

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
