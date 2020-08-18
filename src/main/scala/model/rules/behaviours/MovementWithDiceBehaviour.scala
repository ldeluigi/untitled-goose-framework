package model.rules.behaviours

import engine.events.consumable.{MovementDiceRollEvent, StepMovementEvent}
import model.rules.behaviours.BehaviourRule.BehaviourRuleImpl
import model.rules.operations.Operation


case class MovementWithDiceBehaviour() extends BehaviourRuleImpl[MovementDiceRollEvent](
  operationsStrategy = (events, state) =>
    events.map(e => Operation.trigger(StepMovementEvent(e.result.sum, e.player, e.turn, state.currentCycle)))
)
