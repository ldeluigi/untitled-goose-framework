package model.rules

import model.{MatchState}

trait Operation {

  def possibleOperations: Set[String]

  def checkOperation(operation: String): Boolean

  def solveOperation: MatchState

  def execute(state: MatchState)

}

object Operation {

  // TODO import operations stack from engine and update constructor accordingly

  private class OperationImpl() extends Operation {

    override def possibleOperations: Set[String] = Set()

    override def checkOperation(operation: String): Boolean = {
      possibleOperations.contains(operation)
    }

    override def solveOperation: MatchState = ???

    override def execute(state: MatchState): Unit = ???
  }

  def apply(): Operation = new OperationImpl()
}
