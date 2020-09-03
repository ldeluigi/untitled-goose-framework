package model.actions

import model.events.GameEvent
import model.events.consumable.StepMovementEvent
import model.entities.runtime.GameState

class StepForwardAction() extends Action {

  override def name: String = "Move Forward"

  override def trigger(state: GameState): GameEvent = {
    StepMovementEvent(1, state.currentPlayer, state.currentTurn, state.currentCycle)
  }
}

object StepForwardAction {

  def apply(): StepForwardAction = new StepForwardAction()
}
