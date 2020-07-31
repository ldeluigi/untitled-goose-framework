package model.rules.behaviours

import engine.events.{DiceRollEvent, MovementEvent}
import model.MatchState
import model.rules.BehaviourRule
import model.rules.operations.{Operation, StepForwardOperation}

case class MultipleStepForwardRule() extends BehaviourRule {

  override def name: Option[String] = Some("Multiple StepRule")

  override def applyRule(state: MatchState): Seq[Operation] = {
    state.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)
      .filter(_.isInstanceOf[MovementEvent])
      .map(e => {
        e.consume()
        e.asInstanceOf[MovementEvent]
      })
      .flatMap(e => Seq.fill(e.movement)(StepForwardOperation(e.sourcePlayer)))
  }
}
