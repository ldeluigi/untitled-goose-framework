package model.rules.actionrules

import engine.events.persistent.LoseTurnEvent
import model.actions.{Action, SkipOneTurnAction}
import model.game.GameState
import model.game.GameStateExtensions._
import model.rules.ruleset.RulePriorities.GooseFrameworkPriorities

case class LoseTurnActionRule(private val allOtherActions: Set[Action])
                             (implicit val rulePriorities: GooseFrameworkPriorities)
  extends ActionRule {

  override def allowedActions(state: GameState): Set[ActionAvailability] =
    if (state.currentPlayer.history.only[LoseTurnEvent].nonEmpty)
      allOtherActions.map(ActionAvailability(_, rulePriorities.loseTurnPriority, allowed = false)) +
        ActionAvailability(SkipOneTurnAction(), rulePriorities.loseTurnPriority)
    else Set()

}