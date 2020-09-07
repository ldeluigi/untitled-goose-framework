package untitled.goose.framework.model.rules.cleanup

import untitled.goose.framework.model.entities.runtime.GameStateExtensions._
import untitled.goose.framework.model.entities.runtime.MutableGameState
import untitled.goose.framework.model.events.consumable.TurnShouldEndEvent
import untitled.goose.framework.model.events.persistent.{GainTurnEvent, TurnEndedEvent}

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
        state.currentPlayer.history = state.currentPlayer.history.skipOfType[GainTurnEvent]()
      }
    }
  }
}
