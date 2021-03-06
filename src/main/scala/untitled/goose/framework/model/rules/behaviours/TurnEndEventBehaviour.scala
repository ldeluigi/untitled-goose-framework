package untitled.goose.framework.model.rules.behaviours

import untitled.goose.framework.model.events.consumable.TurnShouldEndEvent
import untitled.goose.framework.model.rules.behaviours.BehaviourRule.BehaviourRuleImpl
import untitled.goose.framework.model.rules.operations.Operation

/**
 * TurnEndEventBehaviour is a BehaviourRule that uses TurnShouldEndEvent(s)
 * to declare, for each player, that every turn must end, with a TurnShouldEndEvent.
 */
private[rules] case class TurnEndEventBehaviour() extends BehaviourRuleImpl[TurnShouldEndEvent](
  countStrategy = _ == 0,
  operationsStrategy = (_, state) => Seq(Operation.trigger(TurnShouldEndEvent(state.currentTurn, state.currentCycle))),
  consume = false, save = false
)