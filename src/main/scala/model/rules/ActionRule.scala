package model.rules

import model.game.MutableGameState

trait ActionRule {

  def allowedActions(state: MutableGameState): Set[ActionAvailability]

}

object ActionRule {

  private class ActionRuleImpl() extends ActionRule {
    override def allowedActions(state: MutableGameState): Set[ActionAvailability] = Set()
  }

  def apply(): ActionRule = new ActionRuleImpl()
}
