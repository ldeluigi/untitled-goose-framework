package untitled.goose.framework.model.actions

import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.StepMovementEvent

/** This very simple action triggers a 1-step [[StepMovementEvent]] for the current player. */
private class StepForwardAction() extends Action {

  override def name: String = "Move Forward"

  override def trigger(state: GameState): GameEvent =
    StepMovementEvent(1, state.currentPlayer, state.currentTurn, state.currentCycle)
}

object StepForwardAction {

  /** This factory instantiates a StepForwardAction. */
  def apply(): Action = new StepForwardAction()
}
