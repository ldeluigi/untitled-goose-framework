package model.rules.actionrules

import model.actions.Action
import model.game.MutableGameState
import model.rules.{ActionAvailability, ActionRule}

/** Model concepts of always-something actions. */
object AlwaysActionRule {

  /** Sets an action to be always permitted.
   *
   * @param priority the priority to be set to the action
   * @param actions  the action to always permit and set priority to
   */
  case class AlwaysPermittedActionRule(priority: Int, actions: Action*) extends ActionRule {
    override def allowedActions(state: MutableGameState): Set[ActionAvailability] = {
      actions.map(ActionAvailability(_, priority)).toSet
    }
  }

  /** Sets an action to be never permitted.
   *
   * @param priority the priority to be set to the action
   * @param actions  the action to always permit and set priority to
   */
  case class AlwaysNegatedActionRule(priority: Int, actions: Action*) extends ActionRule {
    override def allowedActions(state: MutableGameState): Set[ActionAvailability] = {
      actions.map(ActionAvailability(_, priority, allowed = false)).toSet
    }
  }

}
