package view.actionmenu

import model.actions.Action
import scalafx.scene.layout.{Pane, VBox}
import view.ApplicationController
import view.board.BoardDisplay

trait ActionMenu extends Pane {

  def displayActions(actions: Set[Action])

}

object ActionMenu {

  private class ActionMenuImpl(boardView: BoardDisplay, controller: ApplicationController) extends ActionMenu {

    val actionBox = new VBox(15)
    this.children.add(actionBox)

    override def displayActions(actions: Set[Action]): Unit = {
      actionBox.children.removeAll(actionBox.children)
      for (a <- actions) {
        actionBox.children.add(ActionVisualization(a, controller))
      }
    }
  }

  def apply(boardView: BoardDisplay, controller: ApplicationController): ActionMenu = new ActionMenuImpl(boardView, controller)
}
