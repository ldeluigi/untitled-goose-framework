package model.rules.actionrules

import model.MatchState
import model.actions.{Action, SkipOneTurnAction}
import model.rules.ruleset.RulePriorities
import model.rules.{ActionAvailability, ActionRule}

case class LoseTurnActionRule(allOtherActions: Set[Action]) extends ActionRule {

  override def allowedActions(state: MatchState): Set[ActionAvailability] = {
    allOtherActions.map(ActionAvailability(_, RulePriorities.loseTurnPriority, allowed = false)) +
      ActionAvailability(SkipOneTurnAction(), RulePriorities.loseTurnPriority)
  }

}