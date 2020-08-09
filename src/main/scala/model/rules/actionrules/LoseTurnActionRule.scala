package model.rules.actionrules

import model.MutableMatchState
import model.actions.{Action, SkipOneTurnAction}
import model.rules.ruleset.RulePriorities
import model.rules.{ActionAvailability, ActionRule}

case class LoseTurnActionRule(allOtherActions: Set[Action]) extends ActionRule {

  //TODO add check on history
  override def allowedActions(state: MutableMatchState): Set[ActionAvailability] = {
    allOtherActions.map(ActionAvailability(_, RulePriorities.loseTurnPriority, allowed = false)) +
      ActionAvailability(SkipOneTurnAction(), RulePriorities.loseTurnPriority)
  }

}