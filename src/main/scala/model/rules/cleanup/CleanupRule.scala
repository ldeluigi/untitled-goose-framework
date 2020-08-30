package model.rules.cleanup

import model.game.MutableGameState

trait CleanupRule {

  def applyRule(state: MutableGameState): Unit

}
