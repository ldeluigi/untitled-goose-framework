package model.rules.actionrules

import model.MatchState
import model.actions.Action
import model.rules.{ActionAvailability, ActionRule}

object AlwaysActionRule {

  case class AlwaysPermittedActionRule(priority: Int, actions: Action*) extends ActionRule {
    override def allowedActions(state: MatchState): Set[ActionAvailability] = {
      actions.map(ActionAvailability(_, priority, allowed = true)).toSet
    }
  }

  case class AlwaysNegatedActionRule(priority: Int, actions: Action*) extends ActionRule {
    override def allowedActions(state: MatchState): Set[ActionAvailability] = {
      actions.map(ActionAvailability(_, priority, allowed = false)).toSet
    }
  }

}
