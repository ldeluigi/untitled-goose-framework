package model.actions

import engine.core.EventSink
import engine.events.StepMovementEvent
import engine.events.root.GameEvent
import model.game.MutableGameState

/** Models the ability to make a player take a step forward during the game. */
class StepForwardAction() extends Action {

  override def name: String = "Move Forward"

  override def execute(sink: EventSink[GameEvent], state: MutableGameState): Unit = {
    sink.accept(StepMovementEvent(1, state.currentPlayer, state.currentTurn))
  }
}

object StepForwardAction {

  /** A factory which creates a new step forward action. */
  def apply(): StepForwardAction = new StepForwardAction()
}
