package model.rules.behaviours

import engine.events.{DiceRollEvent, StepMovementEvent}
import model.MatchState
import model.rules.BehaviourRule
import model.rules.operations.Operation

case class MovementWithDiceBehaviour() extends BehaviourRule {

  override def name: Option[String] = None

  override def applyRule(matchState: MatchState): Seq[Operation] = {
    matchState.currentPlayer.history
      .filter(_.turn == matchState.currentTurn)
      .filter(!_.isConsumed)
      .filter(_.isInstanceOf[DiceRollEvent[Any]]) //TODO SOLVE THIS WARNING
      .map(e => {
        e.consume()
        e.asInstanceOf[DiceRollEvent[Int]]
      })
      .map(e => Operation.trigger(s => Some(StepMovementEvent(e.result, e.player, s.currentTurn))))
  }

}
