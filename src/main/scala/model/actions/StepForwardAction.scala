package model.actions

import engine.`match`.Match
import engine.events.MovementEvent
import engine.events.core.EventSink
import engine.events.root.GameEvent
import model.Player

class StepForwardAction() extends Action {

  override def name: String = "Move Forward"

  override def execute(sink: EventSink[GameEvent], state: Match): Unit = {
    sink.accept(MovementEvent(1, state.currentState.currentPlayer))
  }
}

object StepForwardAction {
  def apply(): StepForwardAction = new StepForwardAction()
}
