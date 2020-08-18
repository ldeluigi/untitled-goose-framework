package model.rules.behaviours

import engine.events.consumable.DialogLaunchEvent
import model.rules.behaviours.BehaviourRule.BehaviourRuleImpl
import model.rules.operations.Operation.DialogOperation

private[rules] case class DialogLaunchBehaviour() extends BehaviourRuleImpl[DialogLaunchEvent](
  operationsStrategy = _.map(e => DialogOperation(e.createDialog))
)
