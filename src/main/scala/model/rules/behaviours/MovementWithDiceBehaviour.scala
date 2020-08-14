package model.rules.behaviours

import engine.events.{DialogLaunchEvent, MovementDiceRollEvent, StepMovementEvent}
import model.`match`.MatchState
import model.rules.BehaviourRule
import model.rules.operations.Operation
import model.`match`.MatchStateExtensions.PimpedHistory

case class MovementWithDiceBehaviour() extends BehaviourRule {

  override def name: Option[String] = None

  override def applyRule(implicit matchState: MatchState): Seq[Operation] = {
    matchState.currentPlayer.history
      .filterCurrentTurn()
      .filterNotConsumed()
      .filter(_.isInstanceOf[MovementDiceRollEvent])
      .map(_.asInstanceOf[MovementDiceRollEvent])
      .consumeAll()
      .map(e => Operation.trigger(s => Some(StepMovementEvent(e.result.sum, e.source, s.currentTurn))))
  }

}
