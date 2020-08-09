package model.rules.behaviours

import engine.events.{DiceRollEvent, StepMovementEvent}
import model.MutableMatchState
import model.rules.BehaviourRule
import model.rules.operations.Operation

case class MovementWithDiceBehaviour() extends BehaviourRule {

  override def name: Option[String] = None

  override def applyRule(matchState: MutableMatchState): Seq[Operation] = {
    matchState.currentPlayer.history
      .filter(_.turn == matchState.currentTurn)
      .filter(!_.isConsumed)
      .filter(_.isInstanceOf[DiceRollEvent[Any]]) //TODO SOLVE THIS WARNING
      .map(e => {
        e.consume()
        e.asInstanceOf[DiceRollEvent[Int]]
      })
      .map(e => Operation.trigger(s => Some(StepMovementEvent(e.result, e.source, s.currentTurn))))
  }

}
