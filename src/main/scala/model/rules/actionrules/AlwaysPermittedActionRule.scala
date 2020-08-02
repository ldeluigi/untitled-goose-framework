package model.rules.actionrules

import model.MatchState
import model.actions.Action
import model.rules.{ActionAvailability, ActionRule}

case class AlwaysPermittedActionRule(actions: Action*) extends ActionRule {
  override def allowedActions(state: MatchState): Set[ActionAvailability] = {
    actions.map(ActionAvailability(_, 1)).toSet
  }
}
