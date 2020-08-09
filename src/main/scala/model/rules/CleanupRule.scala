package model.rules

import model.MutableMatchState

trait CleanupRule {

  def applyRule(state: MutableMatchState): Unit

}
