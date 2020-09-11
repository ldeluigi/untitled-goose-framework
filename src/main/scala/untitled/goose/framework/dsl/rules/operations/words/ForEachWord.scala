package untitled.goose.framework.dsl.rules.operations.words

import untitled.goose.framework.dsl.rules.operations.nodes.OperationNode
import untitled.goose.framework.model.entities.runtime.{GameState, MutableGameState}
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

case class ForEachWord() extends OperationWords {
  override def trigger[T <: ConsumableGameEvent](event: (T, GameState) => GameEvent): OperationNode[T] = ???

  override def displayMessage[T <: ConsumableGameEvent](dialog: (T, GameState) => (String, String)): OperationNode[T] = ???

  override def displayMessage[T <: ConsumableGameEvent](title: String, text: String): OperationNode[T] = ???

  override def displayQuestion[T <: ConsumableGameEvent](title: String, text: String, options: (String, GameState => GameEvent)*): OperationNode[T] = ???

  override def displayQuestion[T <: ConsumableGameEvent](dialog: (T, GameState) => (String, String, Seq[(String, GameEvent)])): OperationNode[T] = ???

  override def updateState[T <: ConsumableGameEvent](update: (T, MutableGameState) => Unit): OperationNode[T] = ???
}
