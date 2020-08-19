package model.actions

import engine.core.EventSink
import engine.events.GameEvent
import engine.events.consumable.SkipTurnEvent
import model.game.MutableGameState

class SkipOneTurnAction extends Action {

  override def name: String = "Skip one turn"

  override def execute(sink: EventSink[GameEvent], state: MutableGameState): Unit = {
    sink.accept(SkipTurnEvent(state.currentPlayer, state.currentTurn, state.currentCycle))
  }
}

object SkipOneTurnAction {
  def apply(): SkipOneTurnAction = new SkipOneTurnAction()
}
