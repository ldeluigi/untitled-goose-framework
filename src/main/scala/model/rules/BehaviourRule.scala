package model.rules

import model.MatchState
import model.rules.operations.Operation

trait BehaviourRule {
  def name: Option[String]

  def applyRule(state: MatchState): Seq[Operation]
}
