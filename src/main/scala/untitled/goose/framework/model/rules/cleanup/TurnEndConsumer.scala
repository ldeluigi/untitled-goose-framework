package untitled.goose.framework.model.rules.cleanup

import untitled.goose.framework.model.entities.runtime.GameStateExtensions._
import untitled.goose.framework.model.entities.runtime.functional.GameStateUpdate.GameStateUpdateImpl
import untitled.goose.framework.model.entities.runtime.{GameState, Player}
import untitled.goose.framework.model.events.consumable.TurnShouldEndEvent
import untitled.goose.framework.model.events.persistent.{GainTurnEvent, TurnEndedEvent}

/**
 * TurnEndConsumer is a CleanupRule that looks for TurnShouldEndEvent for the current player
 * and, if it finds one relative to the current turn, consumes it and ends the turn, moving the game
 * to the next player.
 */
case class TurnEndConsumer(nextPlayerStrategy: (Player, Seq[Player]) => Player) extends CleanupRule {

  override def applyRule(state: GameState): GameState =
    consumeTurn(state)

  private def consumeTurn(state: GameState): GameState = {
    val shouldEnd = state.consumableBuffer
      .filterTurn(state.currentTurn)
      .only[TurnShouldEndEvent].nonEmpty

    val shouldPassTurn = state.currentPlayer.history
      .only[GainTurnEvent]
      .isEmpty

    if (shouldEnd) {
      val s = state.submitEvent(TurnEndedEvent(state.currentPlayer, state.currentTurn, state.currentCycle))
      if (shouldPassTurn) {
        s.updateCurrentTurn(_ + 1)
          .updateCurrentPlayer(nextPlayerStrategy)
      } else {
        s.updatePlayerHistory(state.currentPlayer, _.skipOfType[GainTurnEvent]())
      }
    } else state
  }
}
