package model.actions

import engine.core.EventSink
import engine.events.StepMovementEvent
import engine.events.root.GameEvent
import model.game.MutableGameState

class StepForwardAction() extends Action {

  override def name: String = "Move Forward"

  override def execute(sink: EventSink[GameEvent], state: MutableGameState): Unit = {
    sink.accept(StepMovementEvent(1, state.currentPlayer, state.currentTurn))
  }
}

object StepForwardAction {
  def apply(): StepForwardAction = new StepForwardAction()
}
