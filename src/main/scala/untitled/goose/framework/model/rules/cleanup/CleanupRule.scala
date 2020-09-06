package untitled.goose.framework.model.rules.cleanup

import untitled.goose.framework.model.entities.runtime.MutableGameState

trait CleanupRule {

  def applyRule(state: MutableGameState): Unit

}
