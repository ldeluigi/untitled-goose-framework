package model.rules.cleanup

import model.entities.runtime.MutableGameState

trait CleanupRule {

  def applyRule(state: MutableGameState): Unit

}
