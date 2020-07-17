package model.rules

import model.MatchState
import model.actions.Action

trait ActionRule {
  def allowedActions(gameState: MatchState): Set[ActionAvailability]
}

object ActionRule {

  private class ActionRuleImpl() extends ActionRule {
    override def allowedActions(gameState: MatchState): Set[ActionAvailability] = Set()
  }

  def apply(): ActionRule = new ActionRuleImpl()
}
