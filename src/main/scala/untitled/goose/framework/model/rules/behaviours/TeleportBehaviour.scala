package untitled.goose.framework.model.rules.behaviours

import untitled.goose.framework.model.events.consumable.TeleportEvent
import untitled.goose.framework.model.rules.behaviours.BehaviourRule.BehaviourRuleImpl
import untitled.goose.framework.model.rules.operations.update.TeleportOperation

/**
 * TeleportBehaviour is an extension of BehaviourRuleImpl and deals with a teleport operation.
 */
case class TeleportBehaviour() extends BehaviourRuleImpl[TeleportEvent](
  operationsStrategy = (events, state) => events.flatMap(e => TeleportOperation(state, e.player, e.tile)),
  consume = true, save = false
)
