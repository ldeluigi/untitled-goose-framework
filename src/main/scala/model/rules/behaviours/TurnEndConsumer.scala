package model.rules.behaviours

import engine.`match`.Match
import engine.events.{DoNotPassTurnEvent, TurnShouldEndEvent}
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
      val eventList = state.history
        .filter(_.turn == state.currentTurn)
        .filter(!_.isConsumed)

      if (eventList.exists(_.isInstanceOf[TurnShouldEndEvent])) {
        eventList.filter(_.isInstanceOf[TurnShouldEndEvent]).foreach(_.consume())
        s.currentTurn = s.currentTurn + 1
        s.newTurnStarted = true
      }

      if (eventList.exists(_.isInstanceOf[DoNotPassTurnEvent])) {
        eventList.filter(_.isInstanceOf[DoNotPassTurnEvent]).foreach(_.consume())
      } else {
        s.currentPlayer = s.nextPlayer
      }
    }
  }
}
