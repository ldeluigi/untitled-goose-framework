package untitled.goose.framework.dsl.rules.behaviours.nodes

import untitled.goose.framework.dsl.nodes.RuleBookNode
import untitled.goose.framework.dsl.rules.behaviours.words.SaveOrConsumeWord
import untitled.goose.framework.dsl.rules.behaviours.words.SaveOrConsumeWord._
import untitled.goose.framework.dsl.rules.operations.nodes.OperationNode
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent
import untitled.goose.framework.model.rules.behaviours.BehaviourRule
import untitled.goose.framework.model.rules.operations.Operation

import scala.reflect.ClassTag

private[dsl] case class BehaviourNode[EventType <: ConsumableGameEvent : ClassTag]
(
  condition: GameState => Boolean,
  filterStrategy: EventType => Boolean,
  countStrategy: Int => Boolean,
  operationNodes: OperationNode[EventType]*
) extends RuleBookNode {

  private var save = false
  private var consume = false


  def generateBehaviour: BehaviourRule = {
    val operations: (Seq[EventType], GameState) => Seq[Operation] = (e, s) => operationNodes.flatMap(_.getOperations(e, s))
    BehaviourRule(filterStrategy, countStrategy, condition, operations, consume, save)
  }

  def andThen(saveOrConsume: SaveOrConsumeWord): Unit = saveOrConsume match {
    case SaveWord() => save = true
    case ConsumeWord() => consume = true
    case SaveAndConsumeWord() =>
      save = true
      consume = true
  }

  override def check: Seq[String] = operationNodes.flatMap(_.check)
}

