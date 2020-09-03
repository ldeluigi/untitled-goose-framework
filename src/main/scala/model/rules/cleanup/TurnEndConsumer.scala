package model.rules.cleanup

import model.entities.runtime.GameStateExtensions._
import model.entities.runtime.MutableGameState
import model.events.consumable.TurnShouldEndEvent
import model.events.persistent.{GainTurnEvent, TurnEndedEvent}

object TurnEndConsumer extends CleanupRule {

  override def applyRule(state: MutableGameState): Unit =
    consumeTurn(state)

  private def consumeTurn(state: MutableGameState): Unit = {
    val shouldEnd = state.consumableBuffer
      .filterTurn(state.currentTurn)
      .only[TurnShouldEndEvent].nonEmpty

    val shouldPassTurn = state.currentPlayer.history
      .only[GainTurnEvent]
      .isEmpty

    if (shouldEnd) {
      state.submitEvent(TurnEndedEvent(state.currentPlayer, state.currentTurn, state.currentCycle))
      if (shouldPassTurn) {
        state.currentTurn = state.currentTurn + 1
        state.currentPlayer = state.nextPlayer
      } else {
        state.currentPlayer.history = state.currentPlayer.history.remove[GainTurnEvent]()
      }
    }
  }
}
