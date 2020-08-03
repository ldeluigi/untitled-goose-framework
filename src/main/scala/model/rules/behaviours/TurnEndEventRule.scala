package model.rules.behaviours

import engine.events.{StepMovementEvent, TurnShouldEndEvent}
import engine.events.core.EventSink
import engine.events.root.GameEvent
import model.MatchState
import model.rules.BehaviourRule
import model.rules.operations.Operation

case class TurnEndEventRule() extends BehaviourRule {

  override def name: Option[String] = Some("Turn Event Rule")

  override def applyRule(state: MatchState): Seq[Operation] = {
    state.history
      .filter(_.turn == state.currentTurn)
      .find(_.isInstanceOf[TurnShouldEndEvent]) match {
      case None => Seq(triggerEvent(state))
      case _ => Seq()
    }
  }

  private def triggerEvent(state: MatchState): Operation = {
    (_, e: EventSink[GameEvent]) => {
      e.accept(TurnShouldEndEvent(state.currentTurn))
    }
  }

}
