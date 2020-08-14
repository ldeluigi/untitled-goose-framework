package model.rules.cleanup

import engine.events.{GainTurnEvent, TurnEndedEvent, TurnShouldEndEvent}
import model.`match`.MutableMatchState
import model.rules.CleanupRule

object TurnEndConsumer extends CleanupRule {

  override def applyRule(state: MutableMatchState): Unit =
    consumeTurn(state)

  private def consumeTurn(state: MutableMatchState): Unit = {
    val eventList = state.history
      .filter(_.turn == state.currentTurn)
      .filter(!_.isConsumed)

    if (eventList.exists(_.isInstanceOf[TurnShouldEndEvent])) {
      eventList.filter(_.isInstanceOf[TurnShouldEndEvent]).foreach(_.consume())
      state.currentPlayer.history = state.currentPlayer.history :+ TurnEndedEvent(state.currentTurn, state.currentPlayer)
      state.currentTurn = state.currentTurn + 1
      state.newTurnStarted = true

      val playerEvent = state.currentPlayer.history.filter(!_.isConsumed)

      if (playerEvent.exists(_.isInstanceOf[GainTurnEvent])) {
        eventList.filter(_.isInstanceOf[GainTurnEvent]).head.consume()
      } else {
        state.currentPlayer = state.nextPlayer
      }
    }
  }
}
