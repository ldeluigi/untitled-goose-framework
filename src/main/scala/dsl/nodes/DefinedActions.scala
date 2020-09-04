package dsl.nodes

import model.actions.Action

trait DefinedActions {

  def isActionDefined(name: String): Boolean

  def getAction(name: String): Action
}
