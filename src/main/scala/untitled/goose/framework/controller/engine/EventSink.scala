package untitled.goose.framework.controller.engine

import untitled.goose.framework.model.events.GameEvent

/** An EventSink can accept incoming events from the user/players and handles them. */
trait EventSink[E <: GameEvent] {

  /**
   * Submits the event in the event queue. Eventually it will start the handler
   * for that event in an asynchronous fashion.
   * @param event The GameEvent that was fired from the players.
   */
  def accept(event: E): Unit
}
