package model.rules.behaviours

import engine.events.TurnShouldEndEvent
import model.MutableMatchState
import model.rules.BehaviourRule
import model.rules.operations.Operation

case class TurnEndEventBehaviour() extends BehaviourRule {

  override def name: Option[String] = Some("Turn Event Rule")

  override def applyRule(state: MutableMatchState): Seq[Operation] = {
    state.history
      .filter(_.turn == state.currentTurn)
      .find(_.isInstanceOf[TurnShouldEndEvent]) match {
      case None => Seq(Operation.trigger(s => Some(TurnShouldEndEvent(s.currentTurn))))
      case _ => Seq()
    }
  }

}
