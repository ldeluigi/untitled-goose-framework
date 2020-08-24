package engine.core

import engine.events.GameEvent

trait EventSink[E <: GameEvent] {

  def accept(event: E): Unit
}
