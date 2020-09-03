package model.rules.actionrules

import model.entities.runtime.GameState

trait ActionRule {

  def allowedActions(state: GameState): Set[ActionAvailability]

}

object ActionRule {

  private class ActionRuleImpl() extends ActionRule {

    override def allowedActions(state: GameState): Set[ActionAvailability] = Set()

  }

  /** A factory that create a new action rule. */
  def apply(): ActionRule = new ActionRuleImpl()
}
