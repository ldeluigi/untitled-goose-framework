package model.rules.behaviours

import engine.events.consumable._
import model.rules.behaviours.BehaviourRule.BehaviourRuleImpl
import model.rules.operations.update.StepOperation

case class MultipleStepBehaviour() extends BehaviourRuleImpl[StepMovementEvent](
  operationsStrategy = (events, state) => events.flatMap(e => StepOperation(state, e.movement, e.player, e.movement > 0)),
  consume = true, save = false
)