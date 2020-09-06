package untitled.goose.framework.model.rules.behaviours

import untitled.goose.framework.model.events.consumable._
import untitled.goose.framework.model.rules.behaviours.BehaviourRule.BehaviourRuleImpl
import untitled.goose.framework.model.rules.operations.update.StepOperation

case class MultipleStepBehaviour() extends BehaviourRuleImpl[StepMovementEvent](
  operationsStrategy = (events, state) => events.flatMap(e => StepOperation(state, e.movement, e.player, e.movement > 0)),
  consume = true, save = false
)