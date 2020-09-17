package untitled.goose.framework.dsl.rules.actions.nodes

import untitled.goose.framework.model.actions.Action

private[dsl] trait ActionCollection {

  def isActionDefined(name: String): Boolean

  def getAction(name: String): Action
}
