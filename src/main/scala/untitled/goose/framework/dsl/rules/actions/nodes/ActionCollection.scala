package untitled.goose.framework.dsl.rules.actions.nodes

import untitled.goose.framework.model.actions.Action

/**
 * This trait represent a collection of all defined actions.
 * It can be interrogated to ask whether or not an action has been defined and the action.
 */
trait ActionCollection {

  def isActionDefined(name: String): Boolean

  def getAction(name: String): Action
}
