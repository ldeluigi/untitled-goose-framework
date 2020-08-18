package model.rules.operations

import engine.events.GameEvent

object OperationImplicits {

  def eventToOperation(event: GameEvent): Operation = Operation.trigger(event)
}
