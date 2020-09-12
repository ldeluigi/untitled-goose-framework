package untitled.goose.framework.dsl.rules.operations.words

import untitled.goose.framework.dsl.events.words.CustomEventInstance
import untitled.goose.framework.dsl.nodes.RuleBook
import untitled.goose.framework.dsl.rules.operations.nodes.OperationNode
import untitled.goose.framework.dsl.rules.operations.nodes.OperationNode.{CustomEventOperationNode, DisplayDialogOperationNode, TriggerOperationNode, UpdateOperationNode}
import untitled.goose.framework.model.entities.runtime.{GameState, MutableGameState}
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

trait OperationWords {

  // TODO enable trigger for other event types (tile, player)
  def triggerCustom[T <: ConsumableGameEvent](customEvent: (T, GameState) => CustomEventInstance)(implicit ruleBook: RuleBook): OperationNode[T] = {
    CustomEventOperationNode(customEvent, ruleBook.eventDefinitions.gameEventCollection, isForEach = false)
  }

  def trigger[T <: ConsumableGameEvent](event: (T, GameState) => GameEvent): OperationNode[T] =
    TriggerOperationNode(event, isForEach = false)

  def displayMessage[T <: ConsumableGameEvent](dialog: (T, GameState) => (String, String)): OperationNode[T] = {
    val content: (T, GameState) => (String, String, Seq[(String, GameEvent)]) = (e, s) => {
      val d = dialog(e, s)
      (d._1, d._2, Seq())
    }
    DisplayDialogOperationNode(content, isForEach = false)
  }


  def displayMessage[T <: ConsumableGameEvent](title: String, text: String): OperationNode[T] = {
    val dialog: (T, GameState) => (String, String, Seq[(String, GameEvent)]) = (e, s) => (title, text, Seq())
    DisplayDialogOperationNode(dialog, isForEach = false)
  }

  def displayQuestion[T <: ConsumableGameEvent](title: String, text: String, options: (String, GameState => GameEvent)*): OperationNode[T] = {
    val dialog: (T, GameState) => (String, String, Seq[(String, GameEvent)]) = (e, s) => (title, text, options.map(a => (a._1, a._2(s))))
    DisplayDialogOperationNode(dialog, isForEach = false)
  }

  def displayQuestion[T <: ConsumableGameEvent](dialog: (T, GameState) => (String, String, Seq[(String, GameEvent)])): OperationNode[T] =
    DisplayDialogOperationNode(dialog, isForEach = false)

  def updateState[T <: ConsumableGameEvent](update: (T, GameState) => MutableGameState => Unit): OperationNode[T] =
    UpdateOperationNode(update, isForEach = false)
}
