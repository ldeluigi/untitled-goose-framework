package model.actions

import model.events.GameEvent
import model.events.consumable.SkipTurnEvent
import model.game.GameState

class SkipOneTurnAction extends Action {

  override def name: String = "Skip one turn"

  override def trigger(state: GameState): GameEvent = {
    SkipTurnEvent(state.currentPlayer, state.currentTurn, state.currentCycle)
  }
}

object SkipOneTurnAction {

  def apply(): SkipOneTurnAction = new SkipOneTurnAction()
}
