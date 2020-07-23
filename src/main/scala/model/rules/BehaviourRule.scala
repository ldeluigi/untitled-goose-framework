package model.rules

import model.MatchState
import model.rules.operations.Operation

trait BehaviourRule {
  def name: Option[String]

  def applyRule(state: MatchState): Seq[Operation]
}

object BehaviourRule{
  private class BehaviourRuleImpl(operations: Seq[Operation]) extends BehaviourRule{
    override def name: Option[String] = None

    override def applyRule(state: MatchState): Seq[Operation] = operations
  }

  def apply(operationList: Seq[Operation]): BehaviourRule = new BehaviourRuleImpl(operationList)
}
