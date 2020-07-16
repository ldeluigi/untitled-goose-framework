package model.rules

import model.MatchState
import model.actions.Action

trait ActionRule {
  def allowedActions(gameState: MatchState): Set[ActionAvailability]
}
