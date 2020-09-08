package untitled.goose.framework.model.rules.behaviours

import untitled.goose.framework.model.events.consumable.{MovementDiceRollEvent, StepMovementEvent}
import untitled.goose.framework.model.rules.behaviours.BehaviourRule.BehaviourRuleImpl
import untitled.goose.framework.model.rules.operations.Operation

// TODO scaladoc
case class MovementWithDiceBehaviour() extends BehaviourRuleImpl[MovementDiceRollEvent](
  operationsStrategy = (events, state) =>
    events.map(e => Operation.trigger(StepMovementEvent(e.result.sum, e.player, e.turn, state.currentCycle))),
  consume = true, save = true
)
