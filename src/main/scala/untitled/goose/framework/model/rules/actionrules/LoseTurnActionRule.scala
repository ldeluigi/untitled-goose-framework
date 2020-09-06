package untitled.goose.framework.model.rules.actionrules

import untitled.goose.framework.model.actions.{Action, SkipOneTurnAction}
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.entities.runtime.GameStateExtensions._
import untitled.goose.framework.model.events.persistent.LoseTurnEvent
import untitled.goose.framework.model.rules.ruleset.RulePriorities.GooseFrameworkPriorities

case class LoseTurnActionRule(private val allOtherActions: Set[Action])
                             (implicit val rulePriorities: GooseFrameworkPriorities)
  extends ActionRule {

  override def allowedActions(state: GameState): Set[ActionAvailability] =
    if (state.currentPlayer.history.only[LoseTurnEvent].nonEmpty)
      allOtherActions.map(ActionAvailability(_, rulePriorities.loseTurnPriority, allowed = false)) +
        ActionAvailability(SkipOneTurnAction(), rulePriorities.loseTurnPriority)
    else Set()

}