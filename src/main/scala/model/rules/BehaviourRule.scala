package model.rules

import model.`match`.MatchState
import model.rules.operations.Operation

trait BehaviourRule {
  def name: Option[String] = None

  def applyRule(state: MatchState): Seq[Operation]
}
