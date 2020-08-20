package model.rules.behaviours

import engine.events.consumable.ConsumableGameEvent
import model.game.GameState
import model.rules.operations.Operation

import scala.reflect.ClassTag

sealed trait BehaviourRule {
  def applyRule(state: GameState): Seq[Operation]
}


object BehaviourRule {

  import model.game.GameStateExtensions._

  private[behaviours] class BehaviourRuleImpl[T <: ConsumableGameEvent]
  (
    filterStrategy: T => Boolean = (_: T) => true,
    countStrategy: Int => Boolean = _ > 0,
    operationsStrategy: (Seq[T], GameState) => Seq[Operation],
    consume: Boolean = true,
    save: Boolean = false
  )(implicit t: ClassTag[T])
    extends BehaviourRule {

    protected def extraOperations(events: Seq[T]): Seq[Operation] = Seq()

    final override def applyRule(state: GameState): Seq[Operation] = {
      val events = state.consumableEvents
        .filterCycle(state.currentCycle)
        .only[T]
        .filter(filterStrategy)
      if (countStrategy(events.size))
        (if (consume) Seq(Operation.updateState(s => s.consumableEvents = s.consumableEvents.removeAll[T]())) else Seq()) ++
          (if (save) Seq(Operation.updateState(s => events.foreach(s.saveEvent))) else Seq()) ++
          operationsStrategy(events, state)
      else Seq()
    }
  }

  /* TODO FIX DEFAULT PARAMS ERROR
  def apply(eventName: String, filterStrategy: GameEvent => Boolean = (_: GameEvent) => true, countStrategy: Int => Boolean = _ > 0, operations: (Seq[GameEvent], GameState) => Seq[Operation]): BehaviourRule =
    new BehaviourRuleImpl((e: GameEvent) => e.name == eventName && filterStrategy(e), countStrategy, operations)
*/

  def apply[T <: ConsumableGameEvent](filterStrategy: T => Boolean = (_: T) => true, countStrategy: Int => Boolean = _ > 0, operations: (Seq[T], GameState) => Seq[Operation], consume: Boolean = true, save: Boolean = false)(implicit t: ClassTag[T]): BehaviourRule =
    new BehaviourRuleImpl(filterStrategy, countStrategy, operations, consume, save)

}
