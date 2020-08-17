package model.rules.behaviours

import engine.events.{MovementDiceRollEvent, StepMovementEvent}
import model.game.GameState
import model.game.GameStateExtensions.PimpedHistory
import model.rules.BehaviourRule
import model.rules.operations.Operation

case class MovementWithDiceBehaviour() extends BehaviourRule {

  override def name: Option[String] = None

  override def applyRule(matchState: GameState): Seq[Operation] = {
    matchState.currentPlayer.history
      .filterTurn(matchState.currentTurn)
      .filterNotConsumed()
      .filter(_.isInstanceOf[MovementDiceRollEvent])
      .map(_.asInstanceOf[MovementDiceRollEvent])
      .consumeAll()
      .map(e => Operation.trigger(s => Some(StepMovementEvent(e.result.sum, e.source, s.currentTurn))))
  }

}
