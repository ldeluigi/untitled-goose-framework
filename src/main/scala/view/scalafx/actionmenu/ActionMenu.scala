package view.scalafx.actionmenu

import controller.CommandSender
import model.actions.Action
import scalafx.scene.layout.{Pane, VBox}
import view.scalafx.board.BoardDisplay


/** An object that organizes a set of actions into a graphical menu.
 */
trait ActionMenu extends Pane {

  def displayActions(actions: Set[Action])

}

object ActionMenu {

  private class ActionMenuImpl(boardView: BoardDisplay, controller: CommandSender) extends ActionMenu {

    val actionBox = new VBox(15)
    this.children.add(actionBox)

    /** Utility method to add every action into a VBox.
     *
     * @param actions the set containing the actions
     */
    override def displayActions(actions: Set[Action]): Unit = {
      actionBox.children.removeAll(actionBox.children)
      for (a <- actions) {
        actionBox.children.add(ActionVisualization(a, controller))
      }
    }
  }

  /** A factory which creates a new ActionMenu. */
  def apply(boardView: BoardDisplay, controller: CommandSender): ActionMenu = new ActionMenuImpl(boardView, controller)
}
