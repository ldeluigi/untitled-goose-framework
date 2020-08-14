package model.rules.behaviours

import engine.events.TurnShouldEndEvent
import model.`match`.MatchState
import model.`match`.MatchStateExtensions.PimpedHistory
import model.rules.BehaviourRule
import model.rules.operations.Operation

case class TurnEndEventBehaviour() extends BehaviourRule {

  override def name: Option[String] = Some("Turn Event Rule")

  override def applyRule(implicit state: MatchState): Seq[Operation] = {
    state.history
      .filterCurrentTurn()
      .find(_.isInstanceOf[TurnShouldEndEvent]) match {
      case None => Seq(Operation.trigger(s => Some(TurnShouldEndEvent(s.currentTurn))))
      case _ => Seq()
    }
  }

}
