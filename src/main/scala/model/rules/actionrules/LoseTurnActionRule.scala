package model.rules.actionrules

import engine.events.LoseTurnEvent
import model.MutableMatchState
import model.actions.{Action, SkipOneTurnAction}
import model.rules.ruleset.RulePriorities
import model.rules.{ActionAvailability, ActionRule}

case class LoseTurnActionRule(private val allOtherActions: Set[Action]) extends ActionRule {

  override def allowedActions(state: MutableMatchState): Set[ActionAvailability] =
    if (state.currentPlayer.history.filter(!_.isConsumed).exists(_.isInstanceOf[LoseTurnEvent]))
      allOtherActions.map(ActionAvailability(_, RulePriorities.loseTurnPriority, allowed = false)) +
        ActionAvailability(SkipOneTurnAction(), RulePriorities.loseTurnPriority)
    else Set()

}