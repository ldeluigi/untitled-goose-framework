package model.rules

import model.MutableMatchState
import model.rules.operations.Operation

trait BehaviourRule {
  def name: Option[String]

  def applyRule(state: MutableMatchState): Seq[Operation]
}
