package model.actions

import engine.events.GameEvent
import engine.events.consumable.StepMovementEvent
import model.game.GameState

class StepForwardAction() extends Action {

  override def name: String = "Move Forward"

  override def trigger(state: GameState): GameEvent = {
    StepMovementEvent(1, state.currentPlayer, state.currentTurn, state.currentCycle)
  }
}

object StepForwardAction {

  def apply(): StepForwardAction = new StepForwardAction()
}
