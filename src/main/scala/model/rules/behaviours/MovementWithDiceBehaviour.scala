package model.rules.behaviours

import engine.events.{MovementDiceRollEvent, StepMovementEvent}
import model.`match`.MatchState
import model.rules.BehaviourRule
import model.rules.operations.Operation

case class MovementWithDiceBehaviour() extends BehaviourRule {

  override def name: Option[String] = None

  override def applyRule(matchState: MatchState): Seq[Operation] = {
    matchState.currentPlayer.history
      .filter(_.turn == matchState.currentTurn)
      .filter(!_.isConsumed)
      .filter(_.isInstanceOf[MovementDiceRollEvent])
      .map(e => {
        e.consume()
        e.asInstanceOf[MovementDiceRollEvent]
      })
      .map(e => Operation.trigger(s => Some(StepMovementEvent(e.result.sum, e.source, s.currentTurn))))
  }

}
