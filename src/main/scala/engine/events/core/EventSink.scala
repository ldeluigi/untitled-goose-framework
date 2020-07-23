package engine.events.core

import model.GameEvent

trait EventSink[E <: GameEvent] {

  def accept(event: E): Unit
}
