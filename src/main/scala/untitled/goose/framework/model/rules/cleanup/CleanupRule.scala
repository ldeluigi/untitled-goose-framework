package untitled.goose.framework.model.rules.cleanup

import untitled.goose.framework.model.entities.runtime.GameState

/**
 * A rule that is called when the whole buffer/stack is solved.
 */
trait CleanupRule {

  def applyRule(state: GameState): GameState

}

object CleanupRule {
  def apply(f: GameState => GameState): CleanupRule = (state: GameState) => f(state)
}
