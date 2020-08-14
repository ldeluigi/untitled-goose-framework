package model.rules.operations

import engine.core.EventSink
import engine.events.root.GameEvent
import model.game.{GameState, MutableGameState}


trait Operation {
  def execute(state: MutableGameState, eventSink: EventSink[GameEvent]): Unit
}

object Operation {

  def trigger(f: GameState => Option[GameEvent]): Operation = (state: MutableGameState, eventSink: EventSink[GameEvent]) => {
    f(state).foreach(eventSink.accept)
  }

  def execute(f: MutableGameState => Unit): Operation = (state: MutableGameState, _: EventSink[GameEvent]) => {
    f(state)
  }
}
