package model.rules.behaviours

import model.events.consumable.SkipTurnEvent
import model.events.persistent.LoseTurnEvent
import model.entities.runtime.GameStateExtensions.PimpedHistory
import model.rules.behaviours.BehaviourRule.BehaviourRuleImpl
import model.rules.operations.Operation

case class SkipTurnBehaviour() extends BehaviourRuleImpl[SkipTurnEvent](
  operationsStrategy = (events, _) => {
    events.map(e => Operation.updateState(_ => e.player.history = e.player.history.remove[LoseTurnEvent]()))
  },
  consume = true, save = false
)
