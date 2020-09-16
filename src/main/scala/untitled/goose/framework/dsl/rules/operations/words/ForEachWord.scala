package untitled.goose.framework.dsl.rules.operations.words

import untitled.goose.framework.dsl.rules.behaviours.words.BehaviourCustomEventInstance
import untitled.goose.framework.dsl.rules.operations.nodes.OperationNode
import untitled.goose.framework.dsl.rules.operations.nodes.OperationNode._
import untitled.goose.framework.model.entities.runtime.{GameState, MutableGameState}
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

case class ForEachWord() extends OperationWords {

  override def trigger[T <: ConsumableGameEvent](customEvent: BehaviourCustomEventInstance[T]): OperationNode[T] = {
    CustomEventOperationNode(customEvent, isForEach = true)
  }

  override def trigger[T <: ConsumableGameEvent](event: (T, GameState) => GameEvent): OperationNode[T] =
    TriggerOperationNode(event, isForEach = true)

  override def displayMessage[T <: ConsumableGameEvent](dialog: (T, GameState) => (String, String)): OperationNode[T] = {
    val content: (T, GameState) => (String, String, Seq[(String, GameEvent)]) = (e, s) => {
      val d = dialog(e, s)
      (d._1, d._2, Seq())
    }
    DisplayDialogOperationNode(content, isForEach = true)
  }


  override def displayMessage[T <: ConsumableGameEvent](title: String, text: String): OperationNode[T] = {
    val dialog: (T, GameState) => (String, String, Seq[(String, GameEvent)]) = (e, s) => (title, text, Seq())
    DisplayDialogOperationNode(dialog, isForEach = true)
  }

  override def displayQuestion[T <: ConsumableGameEvent](title: String, text: String, options: (String, GameState => GameEvent)*): OperationNode[T] = {
    val dialog: (T, GameState) => (String, String, Seq[(String, GameEvent)]) = (e, s) => (title, text, options.map(a => (a._1, a._2(s))))
    DisplayDialogOperationNode(dialog, isForEach = true)
  }

  override def displayQuestion[T <: ConsumableGameEvent](dialog: (T, GameState) => (String, String, Seq[(String, GameEvent)])): OperationNode[T] =
    DisplayDialogOperationNode(dialog, isForEach = true)

  override def displayQuestion[T <: ConsumableGameEvent](dialog: (T, GameState) => (String, String), options: (String, BehaviourCustomEventInstance[T])*): OperationNode[T] =
    DisplayCustomDialogOperationNode(dialog, options, isForEach = true)

  override def updateState[T <: ConsumableGameEvent](update: (T, GameState) => MutableGameState => Unit): OperationNode[T] =
    UpdateOperationNode(update, isForEach = true)
}
