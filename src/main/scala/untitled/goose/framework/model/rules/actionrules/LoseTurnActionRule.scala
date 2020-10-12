package untitled.goose.framework.model.rules.actionrules

import untitled.goose.framework.model.actions.{Action, SkipOneTurnAction}
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.entities.runtime.GameStateExtensions._
import untitled.goose.framework.model.events.persistent.LoseTurnEvent

/** This action rule negates given actions if a LoseTurnEvent occurs for a player. */
case class LoseTurnActionRule(private val allOtherActions: Set[Action], private val priority: Int)
  extends ActionRule {

  override def allowedActions(state: GameState): Set[ActionAvailability] =
    if (state.players(state.currentPlayer).history.only[LoseTurnEvent].nonEmpty)
      allOtherActions.map(ActionAvailability(_, priority, allowed = false)) +
        ActionAvailability(SkipOneTurnAction(), priority)
    else Set()

}