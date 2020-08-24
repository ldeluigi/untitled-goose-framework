package model.rules

import model.game.GameState
import model.rules.operations.Operation

/** Defines a behaviour rule. */
trait BehaviourRule {

  /** Sets the behaviour rule name.
   *
   * @return the behaviour name, if present
   */
  def name: Option[String] = None

  /** Applies a rule and generates its related subsequent operations.
   *
   * @param state the game state containing the rules to apply
   * @return the sequence of operation related to the rule
   */
  def applyRule(state: GameState): Seq[Operation]
}
