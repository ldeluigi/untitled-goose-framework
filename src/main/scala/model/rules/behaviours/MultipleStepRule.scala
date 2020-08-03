package model.rules.behaviours

import engine.events.{DiceRollEvent, StepMovementEvent}
import model.MatchState
import model.rules.BehaviourRule
import model.rules.operations.{Operation, StepBackwardOperation, StepForwardOperation}

case class MultipleStepRule() extends BehaviourRule {

  override def name: Option[String] = Some("Multiple StepRule")

  override def applyRule(state: MatchState): Seq[Operation] = {
    state.currentPlayer.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
      .filter(_.isInstanceOf[StepMovementEvent])
      .map(e => {
        e.consume()
        e.asInstanceOf[StepMovementEvent]
      })
      .flatMap(e => e.movement match {
        case step if step >= 0 => Seq.fill(step)(StepForwardOperation(e.sourcePlayer))
        case step if step < 0 => Seq.fill(-step)(StepBackwardOperation(e.sourcePlayer))
      })
  }
}
