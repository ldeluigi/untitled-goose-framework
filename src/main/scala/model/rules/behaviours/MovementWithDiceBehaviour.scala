package model.rules.behaviours

import engine.events.{DialogLaunchEvent, MovementDiceRollEvent, StepMovementEvent}
import model.game.GameState
import model.rules.BehaviourRule
import model.rules.operations.Operation
import model.game.GameStateExtensions.PimpedHistory

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
