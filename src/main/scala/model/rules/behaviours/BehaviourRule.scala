package model.rules.behaviours

import engine.events.GameEvent
import model.game.GameState
import model.rules.operations.Operation

import scala.reflect.ClassTag

sealed trait BehaviourRule {
  def applyRule(state: GameState): Seq[Operation]
}


object BehaviourRule {

  import model.game.GameStateExtensions._

  private[behaviours] class BehaviourRuleImpl[T <: GameEvent]
  (
    filterStrategy: GameEvent => Boolean = _ => true,
    countStrategy: Int => Boolean = _ > 0,
    operationsStrategy: (Seq[T], GameState) => Seq[Operation]
  )(implicit t: ClassTag[T])
    extends BehaviourRule {
    override def applyRule(state: GameState): Seq[Operation] = {
      val events = state.consumableEvents
        .filter(filterStrategy)
        .filterCycle(state.currentCycle)
        .only[T]
      if (countStrategy(events.size)) {
        operationsStrategy(events, state)
      } else Seq()
    }
  }

  def apply(eventName: String, countStrategy: Int => Boolean = _ > 0, operations: (Seq[GameEvent], GameState) => Seq[Operation]): BehaviourRule =
    new BehaviourRuleImpl(_.name == eventName, countStrategy, operations)

  def apply[T <: GameEvent](countStrategy: Int => Boolean = _ > 0, operations: (Seq[T], GameState) => Seq[Operation])(implicit t: ClassTag[T]): BehaviourRule =
    new BehaviourRuleImpl(_ => true, countStrategy, operations)

}
