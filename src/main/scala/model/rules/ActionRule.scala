package model.rules

import model.game.MutableGameState

/** Defines the concept of an ActionRule. */
trait ActionRule {

  /** Allowed actions for an action rule.
   *
   * @param state the current MutableGameState
   * @return the set of allowed actions
   */
  def allowedActions(state: MutableGameState): Set[ActionAvailability]

}

object ActionRule {

  private class ActionRuleImpl() extends ActionRule {

    override def allowedActions(state: MutableGameState): Set[ActionAvailability] = Set()

  }

  /** A factory that create a new action rule. */
  def apply(): ActionRule = new ActionRuleImpl()
}
