package model.rules.behaviours

import engine.events.consumable.SkipTurnEvent
import engine.events.persistent.player.LoseTurnEvent
import model.game.GameStateExtensions.PimpedHistory
import model.rules.behaviours.BehaviourRule.BehaviourRuleImpl
import model.rules.operations.Operation

case class SkipTurnBehaviour() extends BehaviourRuleImpl[SkipTurnEvent](
  operationsStrategy = (events, _) => {
    events.map(e => Operation.updateState(_ => e.player.history = e.player.history.remove[LoseTurnEvent]()))
  }
)
