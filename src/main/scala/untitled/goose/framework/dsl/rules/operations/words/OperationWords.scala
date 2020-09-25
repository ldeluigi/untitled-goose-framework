package untitled.goose.framework.dsl.rules.operations.words

import untitled.goose.framework.dsl.rules.behaviours.words.BehaviourCustomEventInstance
import untitled.goose.framework.dsl.rules.operations.nodes.OperationNode
import untitled.goose.framework.dsl.rules.operations.nodes.OperationNode._
import untitled.goose.framework.model.entities.runtime.{GameState, MutableGameState}
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

trait OperationWords {

  /** Triggers a custom event. */
  def trigger[T <: ConsumableGameEvent](customEvent: BehaviourCustomEventInstance[T]): OperationNode[T] = {
    CustomEventOperationNode(customEvent, isForEach = false)
  }

  /** Triggers an event. */
  def trigger[T <: ConsumableGameEvent](event: (T, GameState) => GameEvent): OperationNode[T] =
    TriggerOperationNode(event, isForEach = false)

  /** Displays a message based on state. */
  def displayMessage[T <: ConsumableGameEvent](dialog: (T, GameState) => (String, String)): OperationNode[T] = {
    val content: (T, GameState) => (String, String, Seq[(String, GameEvent)]) = (e, s) => {
      val d = dialog(e, s)
      (d._1, d._2, Seq())
    }
    DisplayDialogOperationNode(content, isForEach = false)
  }

  /** Displays a message based on state at evaluation tine. */
  def displayMessage[T <: ConsumableGameEvent](title: String, text: String): OperationNode[T] = {
    val dialog: (T, GameState) => (String, String, Seq[(String, GameEvent)]) = (e, s) => (title, text, Seq())
    DisplayDialogOperationNode(dialog, isForEach = false)
  }

  /** Displays a question for the user. The question generates a game event based on answer and state. */
  def displayQuestion[T <: ConsumableGameEvent](title: String, text: String, options: (String, GameState => GameEvent)*): OperationNode[T] = {
    val dialog: (T, GameState) => (String, String, Seq[(String, GameEvent)]) = (e, s) => (title, text, options.map(a => (a._1, a._2(s))))
    DisplayDialogOperationNode(dialog, isForEach = false)
  }

  /** Displays a different question based on state. The user selects one answer and then the corresponding event is fired. */
  def displayQuestion[T <: ConsumableGameEvent](dialog: (T, GameState) => (String, String, Seq[(String, GameEvent)])): OperationNode[T] =
    DisplayDialogOperationNode(dialog, isForEach = false)

  /** Displays a question based on state and fires a custom event based on the answer picked. */
  def displayCustomQuestion[T <: ConsumableGameEvent](dialog: (T, GameState) => (String, String), options: ((T, GameState) => String, BehaviourCustomEventInstance[T])*): OperationNode[T] =
    DisplayCustomDialogOperationNode(dialog, options, isForEach = false)

  /** Updates the state based on previous state and the event that triggered this behaviour. */
  def updateState[T <: ConsumableGameEvent](update: (T, GameState) => MutableGameState => Unit): OperationNode[T] =
    UpdateOperationNode(update, isForEach = false)
}
