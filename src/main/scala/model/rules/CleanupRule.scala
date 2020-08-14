package model.rules

import model.`match`.MutableMatchState

trait CleanupRule {

  def applyRule(state: MutableMatchState): Unit

}
