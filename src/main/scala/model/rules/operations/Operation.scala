package model.rules.operations

import engine.events.core.EventSink
import engine.events.root.GameEvent
import model.MatchState

trait Operation {
  def execute(state: MatchState, eventSink: EventSink[GameEvent]): MatchState
}

object Operation {

  private class OperationImpl() extends Operation {

    override def execute(state: MatchState, eventSink: EventSink[GameEvent]): MatchState = state
  }

  def apply(): Operation = new OperationImpl()
}
