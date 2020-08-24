package model.rules.actionrules

import engine.events.LoseTurnEvent
import model.actions.{Action, SkipOneTurnAction}
import model.game.MutableGameState
import model.rules.ruleset.RulePriorities
import model.rules.{ActionAvailability, ActionRule}

case class LoseTurnActionRule(private val allOtherActions: Set[Action]) extends ActionRule {

  /**
   * @param state a MutableGameState on which to check the actions
   * @return a set of allowrd actions
   */
  override def allowedActions(state: MutableGameState): Set[ActionAvailability] =
    if (state.currentPlayer.history.filter(!_.isConsumed).exists(_.isInstanceOf[LoseTurnEvent]))
      allOtherActions.map(ActionAvailability(_, RulePriorities.loseTurnPriority, allowed = false)) +
        ActionAvailability(SkipOneTurnAction(), RulePriorities.loseTurnPriority)
    else Set()

}