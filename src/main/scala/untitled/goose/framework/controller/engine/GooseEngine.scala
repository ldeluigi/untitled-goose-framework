package untitled.goose.framework.controller.engine

import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.special.{ActionEvent, NoOpEvent}

/**
 * The runtime engine of the game. It can receive events to handle,
 * and can be polled to get the game, which includes the state.
 *
 * It uses a stack for operations.
 */
trait GooseEngine {

  /**
   * Schedules a view update to be run when possible (next time the operation stack is freed).
   *
   * Should be roughly equivalent to eventSink.accept([[NoOpEvent]]).
   */
  def callViewUpdate(): Unit

  /**
   * Returns an event sink that manages incoming asynchronous event.
   *
   * Useful to select actions with [[ActionEvent]].
   */
  def eventSink: EventSink[GameEvent]

  /** Clears resources and stops permanently the engine. */
  def stop(): Unit
}
