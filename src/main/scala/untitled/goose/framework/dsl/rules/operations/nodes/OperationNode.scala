package untitled.goose.framework.dsl.rules.operations.nodes

import untitled.goose.framework.dsl.events.nodes.EventCollection
import untitled.goose.framework.dsl.events.words.CustomEventInstance
import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.entities.runtime.{GameState, MutableGameState}
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent
import untitled.goose.framework.model.rules.operations.Operation
import untitled.goose.framework.model.rules.operations.Operation.DialogOperation

sealed trait OperationNode[T <: ConsumableGameEvent] extends RuleBookNode {
  def getOperations: (Seq[T], GameState) => Seq[Operation]

  def isForEach: Boolean
}
//TODO implement checks
object OperationNode {

  case class CustomEventOperationNode[T <: ConsumableGameEvent](event: (T, GameState) => CustomEventInstance, definedEvents: EventCollection, isForEach: Boolean) extends OperationNode[T] {

    override def getOperations: (Seq[T], GameState) => Seq[Operation] = ??? //TODO Delu pensaci tu

    override def check: Seq[String] = ???

  }

  case class TriggerOperationNode[T <: ConsumableGameEvent](event: (T, GameState) => GameEvent, isForEach: Boolean) extends OperationNode[T] {
    override def getOperations: (Seq[T], GameState) => Seq[Operation] =
      if (isForEach)
        (e, s) => Seq(Operation.trigger(e.map(event(_, s)): _*))
      else
        (e, s) => Seq(Operation.trigger(event(e.head, s)))

    override def check: Seq[String] = Seq()
  }

  case class DisplayDialogOperationNode[T <: ConsumableGameEvent](dialog: (T, GameState) => (String, String, Seq[(String, GameEvent)]), isForEach: Boolean) extends OperationNode[T] {
    override def getOperations: (Seq[T], GameState) => Seq[Operation] =
      if (isForEach)
        (e, s) => e.map(dialog(_, s)).map(c => DialogOperation(DialogContent(c._1, c._2, c._3: _*)))
      else
        (e, s) => {
          val c = dialog(e.head, s)
          Seq(DialogOperation(DialogContent(c._1, c._2, c._3: _*)))
        }

    override def check: Seq[String] = Seq()
  }

  case class UpdateOperationNode[T <: ConsumableGameEvent](f: (T, GameState) => MutableGameState => Unit, isForEach: Boolean) extends OperationNode[T] {
    override def getOperations: (Seq[T], GameState) => Seq[Operation] =
      if (isForEach)
        (e, s) => e.map(ev => Operation.updateState(f(ev, s)))
      else
        (e, s) => Seq(Operation.updateState(f(e.head, s)))


    override def check: Seq[String] = Seq()
  }

}