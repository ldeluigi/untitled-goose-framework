package view

import model.actions.Action
import scalafx.scene.layout.{Pane, VBox}

trait ActionMenu extends Pane {

  def displayActions(actions: Set[Action])

}

object ActionMenu {

  private class ActionMenuImpl(boardView: BoardView, controller: ApplicationController) extends ActionMenu {

    val actionBox = new VBox(15)
    this.children.add(actionBox)

    override def displayActions(actions: Set[Action]): Unit = {
      actionBox.children.removeAll(actionBox.children)
      for (a <- actions) {
        actionBox.children.add(ActionVisualization(a, controller))
      }
    }
  }

  def apply(boardView: BoardView, controller: ApplicationController): ActionMenu = new ActionMenuImpl(boardView, controller)
}
