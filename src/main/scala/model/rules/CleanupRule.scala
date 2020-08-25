package model.rules

import model.game.MutableGameState

trait CleanupRule {

  def applyRule(state: MutableGameState): Unit

}
