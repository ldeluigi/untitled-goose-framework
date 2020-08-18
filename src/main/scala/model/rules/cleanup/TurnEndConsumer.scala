package model.rules.cleanup

import engine.events.consumable.TurnShouldEndEvent
import engine.events.persistent.player.GainTurnEvent
import model.game.GameStateExtensions.PimpedHistory
import model.game.MutableGameState
import model.rules.CleanupRule

object TurnEndConsumer extends CleanupRule {

  override def applyRule(state: MutableGameState): Unit =
    consumeTurn(state)

  private def consumeTurn(state: MutableGameState): Unit = {
    val eventList = state.consumableEvents
      .filterTurn(state.currentTurn)
      .filterNotConsumed()

    if (eventList.exists(_.isInstanceOf[TurnShouldEndEvent])) {
      eventList.filter(_.isInstanceOf[TurnShouldEndEvent]).consumeAll()
      state.currentPlayer.history = state.currentPlayer.history :+ TurnEndedEvent(state.currentPlayer, state.currentTurn)

      val playerEvent = state.currentPlayer.history.filter(!_.isConsumed)

      if (playerEvent.exists(_.isInstanceOf[GainTurnEvent])) {
        eventList.filter(_.isInstanceOf[GainTurnEvent]).head.consume()
      } else {
        state.currentTurn = state.currentTurn + 1
        state.currentPlayer = state.nextPlayer
      }
    }
  }
}
