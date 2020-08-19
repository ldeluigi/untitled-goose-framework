package model.rules.operations

import engine.events.GameEvent
import model.entities.DialogContent
import model.game.MutableGameState

sealed trait Operation {
  def execute(state: MutableGameState): Unit
}

object Operation {

  import model.game.GameStateExtensions.MutableStateExtensions

  def trigger(event: GameEvent*): Operation = new Operation {
    override def execute(state: MutableGameState): Unit = event.foreach(state.submitEvent)
  }

  def updateState(f: MutableGameState => Unit): Operation = new Operation {
    override def execute(state: MutableGameState): Unit = f(state)
  }

  sealed trait SpecialOperation extends Operation

  case class DialogOperation(dialogContent: DialogContent) extends SpecialOperation {

    override def execute(state: MutableGameState): Unit = {}
  }

}
