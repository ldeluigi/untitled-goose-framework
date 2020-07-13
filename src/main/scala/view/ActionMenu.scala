package view

import model.actions.Action

trait ActionMenu {

  def displayActions(actions : List[Action])

}
