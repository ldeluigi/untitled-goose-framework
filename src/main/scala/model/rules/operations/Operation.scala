package model.rules.operations

import engine.core.EventSink
import engine.events.root.GameEvent
import model.game.{GameState, MutableGameState}

/** Models the concept of a game operation. */
trait Operation {

  /** Executes an operation.
   *
   * @param state     the state from which withdraw the operation.
   * @param eventSink the event sink to register the happened event into.
   */
  def execute(state: MutableGameState, eventSink: EventSink[GameEvent]): Unit
}

object Operation {

  /** Triggers a certain operation.
   *
   * @param gameState the GameState from which withdraw the operation to execute
   * @return the operation to be executed
   */
  def trigger(gameState: GameState => Option[GameEvent]): Operation = (state: MutableGameState, eventSink: EventSink[GameEvent]) => {
    gameState(state).foreach(eventSink.accept)
  }

  /** Executes an operation.
   *
   * @param mutableGameState the MutableGameState from which withdraw the operation to execute
   * @return the operation to be executed
   */
  def execute(mutableGameState: MutableGameState => Unit): Operation = (state: MutableGameState, _: EventSink[GameEvent]) => {
    mutableGameState(state)
  }
}
