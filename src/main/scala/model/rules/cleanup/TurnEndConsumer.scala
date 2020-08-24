package model.rules.cleanup

import engine.events.consumable.TurnShouldEndEvent
import engine.events.persistent.{GainTurnEvent, TurnEndedEvent}
import model.game.GameStateExtensions._
import model.game.MutableGameState
import model.rules.CleanupRule

/** Models the concept of an end turn event consumer. */
object TurnEndConsumer extends CleanupRule {

  override def applyRule(state: MutableGameState): Unit =
    consumeTurn(state)

  /** Consumes the turn.
   *
   * @param state the MutableGameState from which consume a turn
   */
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
