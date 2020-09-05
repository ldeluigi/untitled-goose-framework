package dsl.rules.actions.nodes

import model.actions.Action

trait ActionCollection {

  def isActionDefined(name: String): Boolean

  def getAction(name: String): Action
}
