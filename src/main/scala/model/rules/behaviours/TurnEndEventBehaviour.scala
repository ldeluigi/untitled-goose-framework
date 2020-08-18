package model.rules.behaviours

import engine.events.consumable.TurnShouldEndEvent
import model.game.GameState
import model.game.GameStateExtensions.PimpedHistory
import model.rules.operations.Operation

private[rules] case class TurnEndEventBehaviour() extends BehaviourRule {

  override def name: Option[String] = Some("Turn Event Rule")

  override def applyRule(state: GameState): Seq[Operation] = {
    state.consumableEvents
      .filterTurn(state.currentTurn)
      .find(_.isInstanceOf[TurnShouldEndEvent]) match {
      case None => Seq(Operation.trigger(s => Some(TurnShouldEndEvent(s.currentTurn))))
      case _ => Seq()
    }
  }

}
