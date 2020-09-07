package untitled.goose.framework.model.rules.operations

import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.entities.runtime.{GameState, MutableGameState}
import untitled.goose.framework.model.events.GameEvent

/** An operation alters the state in some way. */
sealed trait Operation {

  /** The name of the operation. */
  def name: String

  /** The method that alters the state. */
  def execute(state: MutableGameState): Unit
}

object Operation {

  import untitled.goose.framework.model.entities.runtime.GameStateExtensions.MutableStateExtensions

  /**
   * An operation that triggers multiple events when executed.
   *
   * @param event the list of events to trigger.
   * @return the operation.
   */
  def trigger(event: GameEvent*): Operation = new Operation {
    override def execute(state: MutableGameState): Unit = event.foreach(state.submitEvent)

    val name: String = "Trigger: " + event.map(_.name).mkString(",")
  }

  /**
   * An operation that checks the game state and in case triggers an event.
   *
   * @param condition   the condition to evaluate at runtime.
   * @param createEvent the event to trigger if condition holds true.
   * @return the operation.
   */
  def triggerWhen(condition: GameState => Boolean, createEvent: GameState => Seq[GameEvent]): Operation = new Operation {
    override def execute(state: MutableGameState): Unit =
      if (condition(state))
        createEvent(state).foreach(state.submitEvent)

    val name: String = "Trigger (When)"
  }

  /** An operation that alters state. */
  def updateState(f: MutableGameState => Unit): Operation = new Operation {
    override def execute(state: MutableGameState): Unit = f(state)

    val name: String = "State Update"
  }

  /** Special operations are treated differently by the engine. */
  sealed trait SpecialOperation extends Operation

  /**
   * This operation prompts a dialog to the player.
   *
   * @param content the dialog contents to display.
   */
  case class DialogOperation(content: DialogContent) extends SpecialOperation {

    override def execute(state: MutableGameState): Unit = {}

    val name: String = "DialogOperation (" + content.title + ")"
  }

}
