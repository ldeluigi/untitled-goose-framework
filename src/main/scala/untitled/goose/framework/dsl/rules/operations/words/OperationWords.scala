package untitled.goose.framework.dsl.rules.operations.words

import untitled.goose.framework.dsl.rules.operations.nodes.OperationNode
import untitled.goose.framework.model.entities.runtime.{GameState, MutableGameState}
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

trait OperationWords {

  def trigger[T <: ConsumableGameEvent](event: (T, GameState) => GameEvent): OperationNode[T] = ???

  def displayMessage[T <: ConsumableGameEvent](dialog: (T, GameState) => (String, String)): OperationNode[T] = ???

  def displayMessage[T <: ConsumableGameEvent](title: String, text: String): OperationNode[T] = ???

  def displayQuestion[T <: ConsumableGameEvent](title: String, text: String, options: (String, GameState => GameEvent)*): OperationNode[T] = ???

  def displayQuestion[T <: ConsumableGameEvent](dialog: (T, GameState) => (String, String, Seq[(String, GameEvent)])): OperationNode[T] = ???

  def updateState[T <: ConsumableGameEvent](update: (T, MutableGameState) => Unit): OperationNode[T] = ???
}
