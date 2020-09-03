package model.actions

import model.entities.runtime.GameState
import model.events.GameEvent
import model.events.consumable.SkipTurnEvent

/**
 * This action is a simple way to force a player to
 * confirm the fact that its turn should be skipped.
 * When triggered, it fires a [[SkipTurnEvent]] for the
 * current player.
 */
class SkipOneTurnAction extends Action {

  override def name: String = "Skip one turn"

  override def trigger(state: GameState): GameEvent = {
    SkipTurnEvent(state.currentPlayer, state.currentTurn, state.currentCycle)
  }
}

object SkipOneTurnAction {

  /** This factory instantiates a SkipOneTurnAction. */
  def apply(): SkipOneTurnAction = new SkipOneTurnAction()
}
