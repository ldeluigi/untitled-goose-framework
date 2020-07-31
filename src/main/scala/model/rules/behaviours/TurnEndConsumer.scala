package model.rules.behaviours

import engine.events.TurnShouldEndEvent
import engine.events.core.EventSink
import engine.events.root.GameEvent
import model.MatchState
import model.rules.BehaviourRule
import model.rules.operations.Operation

case class TurnEndConsumer() extends BehaviourRule {
  override def name: Option[String] = Some("TurnEndConsumer")

  override def applyRule(state: MatchState): Seq[Operation] = {
    Seq(consumeTurn(state))
  }

  private def consumeTurn(state: MatchState): Operation = {
    (s: MatchState, _: EventSink[GameEvent]) => {
      var eventList = state.history
        .filter(_.turn == state.currentTurn)
        .filter(!_.isConsumed)
        .filter(_.isInstanceOf[TurnShouldEndEvent])
      if (eventList.nonEmpty) {
        eventList.foreach(_.consume())
        s.currentTurn = s.currentTurn + 1
        s.newTurnStarted = true
      }

      //TODO UPDATE CURRENT PLAYER WITH NEXT ONE!
    }
  }
}
