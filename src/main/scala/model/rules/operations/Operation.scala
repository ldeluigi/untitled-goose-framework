package model.rules.operations

import engine.core.{DialogDisplayer, EventSink}
import engine.events.root.GameEvent
import model.entities.DialogContent
import model.{MatchState, ReadOnlyMatchState}

import scala.concurrent.ExecutionContext


trait Operation {
  def execute(state: MatchState, eventSink: EventSink[GameEvent]): Unit
}

object Operation {

  implicit val context: ExecutionContext = ExecutionContext.global //TODO verify

  def trigger(f: ReadOnlyMatchState => Option[GameEvent]): Operation = (state: MatchState, eventSink: EventSink[GameEvent]) => {
    f(state).foreach(eventSink.accept)
  }

  def execute(f: MatchState => Unit): Operation = (state: MatchState, _: EventSink[GameEvent]) => {
    f(state)
  }
}
