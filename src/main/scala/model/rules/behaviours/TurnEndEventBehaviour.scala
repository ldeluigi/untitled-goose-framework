package model.rules.behaviours

import engine.events.TurnShouldEndEvent
import model.game.GameState
import model.game.GameStateExtensions.PimpedHistory
import model.rules.BehaviourRule
import model.rules.operations.Operation

case class TurnEndEventBehaviour() extends BehaviourRule {

  override def name: Option[String] = Some("Turn Event Rule")

  override def applyRule(state: GameState): Seq[Operation] = {
    state.history
      .filterTurn(state.currentTurn)
      .filterNotConsumed()
      .filter(_.isInstanceOf[TurnShouldEndEvent])
      .consumeAll()
      .headOption match {
      case None => Seq(Operation.trigger(s => Some(TurnShouldEndEvent(s.currentTurn))))
      case _ => Seq()
    }
  }

}
