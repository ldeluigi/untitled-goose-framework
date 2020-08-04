package model.rules.operations

import engine.events.core.EventSink
import engine.events.root.GameEvent
import model.MatchState
import model.entities.DialogContent

sealed trait SpecialOperation extends Operation {

}

class DialogOperation(val content: DialogContent) extends SpecialOperation {
  override def execute(state: MatchState, eventSink: EventSink[GameEvent]): Unit = {}
}
