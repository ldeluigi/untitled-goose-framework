package model.rules.behaviours

import model.events.consumable.TeleportEvent
import model.rules.behaviours.BehaviourRule.BehaviourRuleImpl
import model.rules.operations.update.TeleportOperation

case class TeleportBehaviour() extends BehaviourRuleImpl[TeleportEvent](
  operationsStrategy = (events, state) => events.flatMap(e => TeleportOperation(state, e.player, e.tile)),
  consume = true, save = false
)
