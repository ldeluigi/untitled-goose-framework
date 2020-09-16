package untitled.goose.framework.model.rules.actionrules

import untitled.goose.framework.model.entities.runtime.GameState

/** A rule defining the availability or the unavailability of some actions in a given state. */
trait ActionRule {

  /**
   * Returns a set of ActionAvailabilities based on the state given.
   *
   * @param state the game state.
   * @return a set of action availabilities.
   */
  def allowedActions(state: GameState): Set[ActionAvailability]
}

object ActionRule {

  private class ActionRuleImpl(availabilities: Set[ActionAvailability], when: GameState => Boolean) extends ActionRule {

    override def allowedActions(state: GameState): Set[ActionAvailability] =
      if (when(state)) {
        availabilities
      } else {
        Set()
      }
  }

  /** A factory that creates a new, empty action rule. */
  def apply(): ActionRule = new ActionRuleImpl(Set(), _ => true)

  /**
   * A factory that creates a new action rule that returns
   * the set when game state satisfies a condition.
   *
   * @param availabilities the availabilities that this rule should output.
   * @param when           if evaluation returns true, it will output the availabilities, or else an empty set.
   * @return a new action created with given parameters.
   */
  def apply(availabilities: Set[ActionAvailability], when: GameState => Boolean): ActionRule =
    new ActionRuleImpl(availabilities, when)
}
