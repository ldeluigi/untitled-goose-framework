package model.rules

import model.`match`.MutableMatchState

trait ActionRule {

  def allowedActions(state: MutableMatchState): Set[ActionAvailability]

}

object ActionRule {

  private class ActionRuleImpl() extends ActionRule {
    override def allowedActions(state: MutableMatchState): Set[ActionAvailability] = Set()
  }

  def apply(): ActionRule = new ActionRuleImpl()
}
