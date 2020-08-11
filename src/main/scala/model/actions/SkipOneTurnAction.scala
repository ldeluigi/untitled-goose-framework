package model.actions

import engine.core.EventSink
import engine.events.SkipTurnEvent
import engine.events.root.GameEvent
import model.MutableMatchState

class SkipOneTurnAction extends Action {

  override def name: String = "Skip one turn"

  override def execute(sink: EventSink[GameEvent], state: MutableMatchState): Unit = {
    sink.accept(SkipTurnEvent(state.currentPlayer, state.currentTurn))
  }
}

object SkipOneTurnAction {
  def apply(): SkipOneTurnAction = new SkipOneTurnAction()
}
