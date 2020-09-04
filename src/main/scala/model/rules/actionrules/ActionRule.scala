package model.rules.actionrules

import model.entities.runtime.GameState

trait ActionRule {

  def allowedActions(state: GameState): Set[ActionAvailability]

}

object ActionRule {

  private class ActionRuleImpl(availabilities: Set[ActionAvailability], when: GameState => Boolean) extends ActionRule {

    override def allowedActions(state: GameState): Set[ActionAvailability] = if (when(state)) {
      availabilities
    } else {
      Set()
    }
  }

  /** A factory that create a new action rule. */
  def apply(): ActionRule = new ActionRuleImpl(Set(), _ => true)

  def apply(availabilities: Set[ActionAvailability], when: GameState => Boolean): ActionRule = new ActionRuleImpl(availabilities, when)
}
