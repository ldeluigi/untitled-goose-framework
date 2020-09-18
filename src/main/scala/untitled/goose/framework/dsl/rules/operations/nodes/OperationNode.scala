package untitled.goose.framework.dsl.rules.operations.nodes

import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.dsl.rules.behaviours.words.BehaviourCustomEventInstance
import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.entities.runtime.{GameState, MutableGameState}
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent
import untitled.goose.framework.model.rules.operations.Operation
import untitled.goose.framework.model.rules.operations.Operation.DialogOperation

private[dsl] sealed trait OperationNode[T <: ConsumableGameEvent] extends RuleBookNode {
  def getOperations: (Seq[T], GameState) => Seq[Operation]

  def isForEach: Boolean
}

private[dsl] object OperationNode {

  case class CustomEventOperationNode[T <: ConsumableGameEvent](event: BehaviourCustomEventInstance[T], isForEach: Boolean) extends OperationNode[T] {

    override def getOperations: (Seq[T], GameState) => Seq[Operation] = (events, state) => events.map(e => Operation.trigger(event.generateEvent((state, e))))

    override def check: Seq[String] = event.check

  }

  case class TriggerOperationNode[T <: ConsumableGameEvent](event: (T, GameState) => GameEvent, isForEach: Boolean) extends OperationNode[T] {
    override def getOperations: (Seq[T], GameState) => Seq[Operation] =
      if (isForEach)
        (e, s) => e.map(ev => Operation.trigger(event(ev, s)))
      else
        (e, s) => if (e.nonEmpty) {
          Seq(Operation.trigger(event(e.head, s)))
        } else Seq()

    override def check: Seq[String] = Seq()
  }

  case class DisplayDialogOperationNode[T <: ConsumableGameEvent](dialog: (T, GameState) => (String, String, Seq[(String, GameEvent)]), isForEach: Boolean) extends OperationNode[T] {
    override def getOperations: (Seq[T], GameState) => Seq[Operation] =
      if (isForEach)
        (e, s) => e.map(dialog(_, s)).map(c => DialogOperation(DialogContent(c._1, c._2, c._3: _*)))
      else
        (e, s) => if (e.nonEmpty) {
          val c = dialog(e.head, s)
          Seq(DialogOperation(DialogContent(c._1, c._2, c._3: _*)))
        } else Seq()

    override def check: Seq[String] = Seq()
  }

  case class DisplayCustomDialogOperationNode[T <: ConsumableGameEvent](dialog: (T, GameState) => (String, String, Seq[String]), events: Seq[BehaviourCustomEventInstance[T]], isForEach: Boolean) extends OperationNode[T] {
    override def getOperations: (Seq[T], GameState) => Seq[Operation] = {
      def _createCustomDialog(e: T, s: GameState): DialogOperation = {
        val d = dialog(e, s)
        val options: Seq[(String, GameEvent)] = d._3.map(k => (k, events(d._3.indexOf(k)).generateEvent(s, e)))
        DialogOperation(DialogContent(
          d._1,
          d._2,
          options: _*
        ))
      }

      if (isForEach)
        (evList, s) => evList.map(e => _createCustomDialog(e, s))
      else
        (e, s) => if (e.nonEmpty) Seq(_createCustomDialog(e.head, s)) else Seq()
    }

    override def check: Seq[String] = events.flatMap(_.check)
  }

  case class UpdateOperationNode[T <: ConsumableGameEvent](f: (T, GameState) => MutableGameState => Unit, isForEach: Boolean) extends OperationNode[T] {
    override def getOperations: (Seq[T], GameState) => Seq[Operation] =
      if (isForEach)
        (e, s) => e.map(ev => Operation.updateState(f(ev, s)))
      else
        (e, s) => if (e.nonEmpty) {
          Seq(Operation.updateState(f(e.head, s)))
        } else Seq()


    override def check: Seq[String] = Seq()
  }

}