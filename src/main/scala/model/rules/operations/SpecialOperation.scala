package model.rules.operations

import engine.core.EventSink
import engine.events.root.GameEvent
import model.MutableMatchState
import model.entities.DialogContent

object SpecialOperation {

  sealed trait SpecialOperation extends Operation

  case class DialogOperation(createDialog: MutableMatchState => DialogContent) extends SpecialOperation {
    var content: DialogContent = _

    override def execute(state: MutableMatchState, eventSink: EventSink[GameEvent]): Unit = {
      content = createDialog(state)
    }

  }
}

//class TerminateTurnOperation() extends SpecialOperation {
//  override def execute(state: MutableMatchState, eventSink: EventSink[GameEvent]): Unit = ???
//
//  /*TODO
//    Consume all events and player events in this turn
//    fa avanzare il turno al player successivo
//     */
//}
