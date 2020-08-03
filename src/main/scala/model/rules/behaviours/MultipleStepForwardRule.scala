package model.rules.behaviours

import engine.events.{DiceRollEvent, StepMovementEvent}
import model.MatchState
import model.rules.BehaviourRule
import model.rules.operations.{Operation, StepForwardOperation}

case class MultipleStepForwardRule() extends BehaviourRule {

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
      .flatMap(e => Seq.fill(e.movement)(StepForwardOperation(e.sourcePlayer)))
  }
}
