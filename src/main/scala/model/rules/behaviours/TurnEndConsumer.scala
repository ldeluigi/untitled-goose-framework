package model.rules.behaviours

import engine.events.{GainTurnEvent, TurnShouldEndEvent}
import model.MatchState
import model.rules.BehaviourRule
import model.rules.operations.Operation

case class TurnEndConsumer() extends BehaviourRule {
  override def name: Option[String] = Some("TurnEndConsumer")

  override def applyRule(state: MatchState): Seq[Operation] = {
    Seq(consumeTurn(state))
  }

  private def consumeTurn(state: MatchState): Operation = {
    Operation.execute((s: MatchState) => {
      val eventList = state.history
        .filter(_.turn == state.currentTurn)
        .filter(!_.isConsumed)

      if (eventList.exists(_.isInstanceOf[TurnShouldEndEvent])) {
        eventList.filter(_.isInstanceOf[TurnShouldEndEvent]).foreach(_.consume())
        s.currentTurn = s.currentTurn + 1
        s.newTurnStarted = true
      }

      val playerEvent = state.currentPlayer.history.filter(!_.isConsumed)

      if (playerEvent.exists(_.isInstanceOf[GainTurnEvent])) {
        eventList.filter(_.isInstanceOf[GainTurnEvent]).head.consume()
      } else {
        s.currentPlayer = s.nextPlayer
      }

    })
  }
}
