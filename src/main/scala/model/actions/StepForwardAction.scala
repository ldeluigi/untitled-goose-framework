package model.actions

import model.events.GameEvent
import model.events.consumable.StepMovementEvent
import model.entities.runtime.GameState

/** This very simple action triggers a 1-step [[StepMovementEvent]] for the current player. */
private class StepForwardAction() extends Action {

  override def name: String = "Move Forward"

  override def trigger(state: GameState): GameEvent = {
    StepMovementEvent(1, state.currentPlayer, state.currentTurn, state.currentCycle)
  }
}

object StepForwardAction {

  /** This factory instantiates a StepForwardAction. */
  def apply(): Action = new StepForwardAction()
}
