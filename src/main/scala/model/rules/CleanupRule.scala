package model.rules

import model.game.MutableGameState

/** Creates a cleanup related behaviour rule. */
trait CleanupRule {

  /** Applies a rule.
   *
   * @param state the MutableGameState onto which to apply the rule
   */
  def applyRule(state: MutableGameState): Unit

}
