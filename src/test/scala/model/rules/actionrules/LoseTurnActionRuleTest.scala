package model.rules.actionrules

import mock.MatchMock
import model.actions.StepForwardAction
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class LoseTurnActionRuleTest extends AnyFlatSpec with Matchers {

  val emptyLoseTurnActionRule: LoseTurnActionRule = LoseTurnActionRule(Set())

  val loseTurnActionRule: LoseTurnActionRule = LoseTurnActionRule(Set(StepForwardAction()))

  behavior of "LoseTurnActionRuleTest"

  it should "do nothing without event" in {

    loseTurnActionRule.allowedActions(MatchMock.default.currentState).isEmpty should be(true)
    pending
  }

}
