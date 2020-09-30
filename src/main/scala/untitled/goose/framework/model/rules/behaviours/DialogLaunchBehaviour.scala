package untitled.goose.framework.model.rules.behaviours

import untitled.goose.framework.model.events.consumable.DialogLaunchEvent
import untitled.goose.framework.model.rules.behaviours.BehaviourRule.BehaviourRuleImpl
import untitled.goose.framework.model.rules.operations.Operation.DialogOperation

/**
 * DialogLaunchBehaviour is an extension of BehaviourRuleImpl based on a DialogLaunchEvent
 * and deals with showing a dialog to the player.
 */
private[rules] case class DialogLaunchBehaviour() extends BehaviourRuleImpl[DialogLaunchEvent](
  operationsStrategy = (events, _) => events.map(e => DialogOperation(e.content)),
  consume = true, save = false
)
