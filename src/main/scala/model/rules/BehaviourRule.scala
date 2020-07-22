package model.rules

trait BehaviourRule {
  def name: Option[String]

  def operations: List[Operation]
}

object BehaviourRule{
  private class BehaviourRuleImpl(operationList: List[Operation]) extends BehaviourRule{
    override def name: Option[String] = None

    override def operations: List[Operation] = operationList
  }

  def apply(operationList: List[Operation]): BehaviourRule = new BehaviourRuleImpl(operationList)
}
