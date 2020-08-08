package model.actions

import engine.events.TurnSkippedEvent
import engine.core.EventSink
import engine.events.root.GameEvent
import model.MatchState

class SkipOneTurnAction extends Action {
  override def name: String = "Skip one turn"

  override def execute(sink: EventSink[GameEvent], state: MatchState): Unit = {
    sink.accept(TurnSkippedEvent(state.currentPlayer, state.currentTurn))
  }
}

object SkipOneTurnAction {
  def apply(): SkipOneTurnAction = new SkipOneTurnAction()
}
