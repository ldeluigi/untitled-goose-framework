package model.rules.operations

import engine.core.EventSink
import engine.events.root.GameEvent
import model.{MatchState, MutableMatchState}


trait Operation {
  def execute(state: MutableMatchState, eventSink: EventSink[GameEvent]): Unit
}

object Operation {

  def trigger(f: MatchState => Option[GameEvent]): Operation = (state: MutableMatchState, eventSink: EventSink[GameEvent]) => {
    f(state).foreach(eventSink.accept)
  }

  def execute(f: MutableMatchState => Unit): Operation = (state: MutableMatchState, _: EventSink[GameEvent]) => {
    f(state)
  }
}
