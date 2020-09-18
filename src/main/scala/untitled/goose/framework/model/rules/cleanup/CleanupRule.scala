package untitled.goose.framework.model.rules.cleanup

import untitled.goose.framework.model.entities.runtime.MutableGameState

/**
 * A rule that is called when the whole buffer/stack is solved.
 */
trait CleanupRule {

  def applyRule(state: MutableGameState): Unit

}

object CleanupRule {
  def apply(f: MutableGameState => Unit): CleanupRule = (state: MutableGameState) => f(state)
}
