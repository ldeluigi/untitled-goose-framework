package model.rules.actionrules

import model.game.MutableGameState
import model.actions.Action
import model.rules.{ActionAvailability, ActionRule}

object AlwaysActionRule {

  case class AlwaysPermittedActionRule(priority: Int, actions: Action*) extends ActionRule {
    override def allowedActions(state: MutableGameState): Set[ActionAvailability] = {
      actions.map(ActionAvailability(_, priority)).toSet
    }
  }

  case class AlwaysNegatedActionRule(priority: Int, actions: Action*) extends ActionRule {
    override def allowedActions(state: MutableGameState): Set[ActionAvailability] = {
      actions.map(ActionAvailability(_, priority, allowed = false)).toSet
    }
  }

}
