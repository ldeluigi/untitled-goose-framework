package model.rules

import model.game.GameState
import model.rules.operations.Operation

trait BehaviourRule {
  def name: Option[String] = None

  def applyRule(state: GameState): Seq[Operation]
}
