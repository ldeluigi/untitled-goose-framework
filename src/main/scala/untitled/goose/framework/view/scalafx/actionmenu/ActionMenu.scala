package untitled.goose.framework.view.scalafx.actionmenu

import scalafx.beans.binding.Bindings
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.layout.{StackPane, VBox}
import untitled.goose.framework.controller.GameManager
import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.runtime.Game
import untitled.goose.framework.view.scalafx.board.BoardDisplay


/** An object that organizes a set of actions into a graphical menu.
 */
trait ActionMenu extends StackPane {

  def displayActions(actions: Set[Action])

}

object ActionMenu {

  private class ActionMenuImpl(boardView: BoardDisplay, game: Game, controller: GameManager) extends ActionMenu {

    val actionBox: VBox = new VBox {
      alignment = Pos.Center
      spacing = 20
      padding = Insets(30)
    }

    this.children.add(actionBox)

    val currentPlayerName: Label = new Label {
      style = "-fx-font-size: 13pt"
    }

    /** Utility method to add every action into a VBox.
     *
     * @param actions the set containing the actions
     */
    override def displayActions(actions: Set[Action]): Unit = {
      actionBox.children.removeAll(actionBox.children)
      actionBox.children.add(currentPlayerName)
      currentPlayerName.textProperty().bind(Bindings.createStringBinding(() => "Current player: " + game.currentState.currentPlayer.name))
      for (a <- actions) {
        actionBox.children.add(ActionVisualization(a, controller))
      }
    }
  }

  /** A factory which creates a new ActionMenu. */
  def apply(boardView: BoardDisplay, game: Game, controller: GameManager): ActionMenu = new ActionMenuImpl(boardView, game, controller)
}
