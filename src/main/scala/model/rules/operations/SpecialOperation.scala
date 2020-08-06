package model.rules.operations

import engine.events.core.EventSink
import engine.events.root.GameEvent
import model.MatchState
import model.entities.DialogContent
import model.rules.behaviours.TurnEndConsumer

sealed trait SpecialOperation extends Operation {

}

class DialogOperation(val createDialog: MatchState => DialogContent) extends SpecialOperation {
  var content: DialogContent = _

  override def execute(state: MatchState, eventSink: EventSink[GameEvent]): Unit = {
    content = createDialog(state)
  }

}

class TerminateTurnOperation() extends SpecialOperation {
  override def execute(state: MatchState, eventSink: EventSink[GameEvent]): Unit = ???
    /*TODO
    Consume all events and player events in this turn
    fa avanzare il turno al player successivo
     */
}
