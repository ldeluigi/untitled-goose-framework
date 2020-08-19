package model.rules.behaviours

import engine.events.consumable.TurnShouldEndEvent
import model.rules.behaviours.BehaviourRule.BehaviourRuleImpl
import model.rules.operations.Operation

private[rules] case class TurnEndEventBehaviour() extends BehaviourRuleImpl[TurnShouldEndEvent](
  countStrategy = _ == 0,
  operationsStrategy = (_, state) => Seq(Operation.trigger(TurnShouldEndEvent(state.currentTurn, state.currentCycle)))
)