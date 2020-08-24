package model.actions

import engine.core.EventSink
import engine.events.GameEvent
import engine.events.consumable.SkipTurnEvent
import model.game.MutableGameState

/** Models the possibility to make a player skip a turn during the game. */
class SkipOneTurnAction extends Action {

  override def name: String = "Skip one turn"

  override def execute(sink: EventSink[GameEvent], state: MutableGameState): Unit = {
    sink.accept(SkipTurnEvent(state.currentPlayer, state.currentTurn, state.currentCycle))
  }
}

object SkipOneTurnAction {

  /** A factory which creates a new skip action. */
  def apply(): SkipOneTurnAction = new SkipOneTurnAction()
}
