package model.rules.operations

import model.entities.DialogContent
import model.events.GameEvent
import model.entities.runtime.{GameState, MutableGameState}

sealed trait Operation {
  def name: String

  def execute(state: MutableGameState): Unit
}

object Operation {

  import model.entities.runtime.GameStateExtensions.MutableStateExtensions

  def trigger(event: GameEvent*): Operation = new Operation {
    override def execute(state: MutableGameState): Unit = event.foreach(state.submitEvent)

    val name: String = "Trigger: " + event.map(_.name).mkString(",")
  }

  def triggerWhen(condition: GameState => Boolean, createEvent: GameState => Seq[GameEvent]): Operation = new Operation {
    override def execute(state: MutableGameState): Unit =
      if (condition(state))
        createEvent(state).foreach(state.submitEvent)

    val name: String = "Trigger (When)"
  }

  def updateState(f: MutableGameState => Unit): Operation = new Operation {
    override def execute(state: MutableGameState): Unit = f(state)

    val name: String = "State Update"
  }

  sealed trait SpecialOperation extends Operation

  case class DialogOperation(content: DialogContent) extends SpecialOperation {

    override def execute(state: MutableGameState): Unit = {}

    val name: String = "DialogOperation (" + content.title + ")"
  }

}
