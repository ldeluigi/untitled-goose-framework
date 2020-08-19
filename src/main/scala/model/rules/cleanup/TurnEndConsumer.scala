package model.rules.cleanup

import engine.events.consumable.TurnShouldEndEvent
import engine.events.persistent.player.{GainTurnEvent, TurnEndedEvent}
import model.game.GameStateExtensions._
import model.game.{GameState, MutableGameState}
import model.rules.CleanupRule

object TurnEndConsumer extends CleanupRule {

  override def applyRule(state: MutableGameState): Unit =
    consumeTurn(state)

  private def consumeTurn(state: MutableGameState): Unit = {
    val shouldEnd = state.consumableEvents
      .filterTurn(state.currentTurn)
      .only[TurnShouldEndEvent].nonEmpty

    if (shouldEnd) {
      state.submitEvent(TurnEndedEvent(state.currentPlayer, state.currentTurn, state.currentCycle))
      if (shouldPassTurn(state)) {
        state.currentTurn = state.currentTurn + 1
        state.currentPlayer = state.nextPlayer
      }
    }
  }

  private def shouldPassTurn(state: GameState): Boolean =
    state.currentPlayer.history
      .filterTurn(state.currentTurn)
      .only[GainTurnEvent]
      .isEmpty
}
