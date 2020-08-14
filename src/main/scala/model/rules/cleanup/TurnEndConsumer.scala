package model.rules.cleanup

import engine.events.{GainTurnEvent, TurnEndedEvent, TurnShouldEndEvent}
import model.`match`.MatchStateExtensions.PimpedHistory
import model.`match`.MutableMatchState
import model.rules.CleanupRule

object TurnEndConsumer extends CleanupRule {

  override def applyRule(state: MutableMatchState): Unit =
    consumeTurn(state)

  private def consumeTurn(implicit state: MutableMatchState): Unit = {
    val eventList = state.history
      .filterCurrentTurn()
      .filterNotConsumed()

    if (eventList.exists(_.isInstanceOf[TurnShouldEndEvent])) {
      eventList.only[TurnShouldEndEvent]().consumeAll()
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
