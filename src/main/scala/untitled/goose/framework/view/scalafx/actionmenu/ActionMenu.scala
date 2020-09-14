package untitled.goose.framework.view.scalafx.actionmenu

import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.layout.{HBox, StackPane, VBox}
import untitled.goose.framework.controller.GameManager
import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.runtime.{GameState, Player}
import untitled.goose.framework.view.scalafx.board.BoardDisplay


/** An object that organizes a set of actions into a graphical menu.
 */
trait ActionMenu extends StackPane {

  def displayActions(actions: Set[Action], currentPlayer: Player)

}

object ActionMenu {

  private class ActionMenuImpl(boardView: BoardDisplay, game: GameState, controller: GameManager) extends ActionMenu {

    val currentPlayerName: Label = new Label {}
    currentPlayerName.styleClass.add("currentPlayerName")

    val actionBox: HBox = new HBox {
      alignment = Pos.TopCenter
      spacing = 20
    }
    actionBox.styleClass.add("actionBox")

    val container: VBox = new VBox {
      alignment = Pos.TopCenter
      spacing = 20
      padding = Insets(30)
      children = List(currentPlayerName, actionBox)
    }

    this.children.add(container)

    /** Utility method to add every action into a VBox.
     *
     * @param actions the set containing the actions
     */
    override def displayActions(actions: Set[Action], currentPlayer: Player): Unit = {
      actionBox.children.removeAll(actionBox.children)
      container.children.removeAll(currentPlayerName, actionBox)
      currentPlayerName.text = "Current player: " + currentPlayer.name
      container.children.add(currentPlayerName)
      for (a <- actions) {
        actionBox.children.add(ActionVisualization(a, controller))
        container.children.add(actionBox)
      }
    }
  }

  /** A factory which creates a new ActionMenu. */
  def apply(boardView: BoardDisplay, game: GameState, controller: GameManager): ActionMenu = new ActionMenuImpl(boardView, game, controller)
}
