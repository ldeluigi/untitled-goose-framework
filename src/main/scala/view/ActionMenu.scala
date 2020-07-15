package view

import model.actions.Action
import scalafx.scene.layout.Pane

trait ActionMenu extends Pane {

  def displayActions(actions: Set[Action])

}

object ActionMenu {
  def apply(boardView: BoardView, controller: ApplicationController): ActionMenu = ActionMenuImpl(boardView, controller)
}

case class ActionMenuImpl(boardView: BoardView, controller: ApplicationController) extends ActionMenu {

  override def displayActions(actions: Set[Action]): Unit = {
    for (a <- actions) {
      this.children.add(ActionVisualization(a, controller))
    }
  }
}

