package untitled.goose.framework.model.rules.behaviours

import untitled.goose.framework.model.entities.runtime.{GameState, MutableGameState}
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent
import untitled.goose.framework.model.rules.operations.Operation

import scala.reflect.ClassTag

/** A rule that outputs the operation that should be executed from a given state. */
sealed trait BehaviourRule {
  def applyRule(state: MutableGameState): Seq[Operation]
}


object BehaviourRule {

  import untitled.goose.framework.model.entities.runtime.GameStateExtensions._

  /** The mandatory implementation of a behaviour rule ensures constraints are honored. */
  private[behaviours] class BehaviourRuleImpl[T <: ConsumableGameEvent : ClassTag]
  (
    filterStrategy: T => Boolean = (_: T) => true,
    countStrategy: Int => Boolean = _ > 0,
    operationsStrategy: (Seq[T], GameState) => Seq[Operation],
    when: GameState => Boolean = _ => true,
    consume: Boolean,
    save: Boolean
  )
    extends BehaviourRule {

    final override def applyRule(state: MutableGameState): Seq[Operation] = {
      val events = state.consumableBuffer
        .filterCycle(state.currentCycle)
        .only[T]
        .filter(filterStrategy)
      if (countStrategy(events.size) && when(state)) {
        if (consume) state.consumableBuffer = state.consumableBuffer.excludeEventType[T]()
        (if (save) Seq(Operation.updateState(s => events.foreach(s.saveEvent))) else Seq()) ++
          operationsStrategy(events, state)
      }
      else Seq()
    }
  }

  /**
   * A factory method that creates a BehaviourRule.
   * This BehaviourRule returns the operations that should be executed from a given state,
   * after some events occur.
   *
   * @param filterStrategy applies a filter to the events on the buffer.
   * @param countStrategy  imposes a minimum to the number of event that must be present.
   * @param when           imposes a condition on the state of the game.
   * @param operations     a sequence of operations to execute if the behaviour is activated.
   * @param consume        if true, removes the events from the buffer.
   * @param save           if true, saves the events in the history.
   * @tparam T             events on the buffer that are not of type T are not considered.
   * @return A new BehaviourRule.
   */
  def apply[T <: ConsumableGameEvent](filterStrategy: T => Boolean = (_: T) => true, countStrategy: Int => Boolean = _ > 0, when: GameState => Boolean = _ => true, operations: (Seq[T], GameState) => Seq[Operation], consume: Boolean = true, save: Boolean = false)(implicit t: ClassTag[T]): BehaviourRule =
    new BehaviourRuleImpl(filterStrategy, countStrategy, operations, when, consume, save)

}
