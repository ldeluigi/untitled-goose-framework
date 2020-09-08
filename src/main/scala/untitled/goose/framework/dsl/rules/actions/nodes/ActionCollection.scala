package untitled.goose.framework.dsl.rules.actions.nodes

import untitled.goose.framework.model.actions.Action

trait ActionCollection {

  def isActionDefined(name: String): Boolean

  def getAction(name: String): Action
}
