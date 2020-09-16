package untitled.goose.framework.model.rules.behaviours

import untitled.goose.framework.model.entities.runtime.GameStateExtensions.PimpedHistory
import untitled.goose.framework.model.events.consumable.SkipTurnEvent
import untitled.goose.framework.model.events.persistent.LoseTurnEvent
import untitled.goose.framework.model.rules.behaviours.BehaviourRule.BehaviourRuleImpl
import untitled.goose.framework.model.rules.operations.Operation

/**
 * SkipTurnBehaviour is an extension of BehaviourRuleImpl
 * and takes care of making the player to skip a turn.
 */
case class SkipTurnBehaviour() extends BehaviourRuleImpl[SkipTurnEvent](
  operationsStrategy = (events, _) => {
    events.map(e => Operation.updateState(_ => e.player.history = e.player.history.skipOfType[LoseTurnEvent]()))
  },
  consume = true, save = false
)
