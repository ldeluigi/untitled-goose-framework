package controller.engine

import model.events.GameEvent

trait EventSink[E <: GameEvent] {

  def accept(event: E): Unit
}
