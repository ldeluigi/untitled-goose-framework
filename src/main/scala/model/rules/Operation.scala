package model.rules

trait Operation {
  def possibleOperations: Set[String]
}

object Operation {

  private class OperationImpl() extends Operation {
    def possibleOperations: Set[String] = Set()
  }

  def apply(): Operation = new OperationImpl()
}
