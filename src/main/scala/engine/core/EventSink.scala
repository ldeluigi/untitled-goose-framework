package engine.core

import engine.events.root.GameEvent

trait EventSink[E <: GameEvent] {

  def accept(event: E): Unit
}
