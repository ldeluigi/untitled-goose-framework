package untitled.goose.framework.model.rules.actionrules

import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.runtime.GameState

object AlwaysActionRule {

  case class AlwaysPermittedActionRule(priority: Int, actions: Action*) extends ActionRule {
    override def allowedActions(state: GameState): Set[ActionAvailability] = {
      actions.map(ActionAvailability(_, priority)).toSet
    }
  }

  case class AlwaysNegatedActionRule(priority: Int, actions: Action*) extends ActionRule {
    override def allowedActions(state: GameState): Set[ActionAvailability] = {
      actions.map(ActionAvailability(_, priority, allowed = false)).toSet
    }
  }

}
