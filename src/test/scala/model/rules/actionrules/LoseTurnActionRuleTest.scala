package model.rules.actionrules

import engine.events.LoseTurnEvent
import mock.MatchMock
import model.actions.StepForwardAction
import model.rules.ActionAvailability
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class LoseTurnActionRuleTest extends AnyFlatSpec with Matchers {

  val emptyLoseTurnActionRule: LoseTurnActionRule = LoseTurnActionRule(Set())

  val loseTurnActionRule: LoseTurnActionRule = LoseTurnActionRule(Set(StepForwardAction()))

  behavior of "LoseTurnActionRuleTest"

  it should "do nothing without event" in {
    loseTurnActionRule.allowedActions(MatchMock.default.currentState).isEmpty should be(true)
  }

  it should "deny actions with lose turn event" in {
    val matchMock = MatchMock.default
    matchMock.submitEvent(LoseTurnEvent(matchMock.players.head, matchMock.currentState.currentTurn))
    loseTurnActionRule.allowedActions(MatchMock.default.currentState).exists(_ match {
      case ActionAvailability(false, _, action) if action.isInstanceOf[StepForwardAction] => true
      case _ => false
    })

  }

}
