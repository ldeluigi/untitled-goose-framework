package model.rules

import model.MatchState
import model.actions.Action

trait ActionAvailability {
  def action: Action

  def allowed: Boolean

  def priority: Int
}

trait ActionRule {
  def allowedActions(gameState: MatchState): Set[ActionAvailability]
}
