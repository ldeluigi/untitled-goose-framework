package model.rules.operations

import engine.events.core.EventSink
import engine.events.root.GameEvent
import model.{MatchState, ReadOnlyMatchState}

trait Operation {
  def execute(state: MatchState, eventSink: EventSink[GameEvent]): Unit
}

object Operation {
  def trigger(f: ReadOnlyMatchState => Option[GameEvent]): Operation = (state: MatchState, eventSink: EventSink[GameEvent]) => {
    f(state).foreach(eventSink.accept)
  }

  def execute(f: MatchState => Unit): Operation = (state: MatchState, _: EventSink[GameEvent]) => {
    f(state)
  }
}
