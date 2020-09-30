package untitled.goose.framework.view.scalafx.actionmenu

import scalafx.geometry.Pos
import scalafx.scene.control.Label
import scalafx.scene.layout.{HBox, StackPane, VBox}
import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.runtime.{GameState, Player}
import untitled.goose.framework.view.InputManager
import untitled.goose.framework.view.scalafx.board.BoardDisplay


/** A panel that organizes a set of player's available actions, and the current player into a graphical menu.
 */
trait ActionMenu extends StackPane {

  /** Sets all player's information into the panel itself, generating custom buttons based on the actions passed to it.
   *
   * @param actions       the set of actions that needs a custom button to trigger.
   * @param currentPlayer the current player playing the game.
   */
  def displayActions(actions: Set[Action], currentPlayer: Player)

}

object ActionMenu {

  private class ActionMenuImpl(boardView: BoardDisplay, game: GameState, controller: InputManager) extends ActionMenu {

    private val currentPlayerName: Label = new Label {}
    currentPlayerName.styleClass.add("currentPlayerName")

    private val actionBox: HBox = new HBox {
      alignment = Pos.TopCenter
      spacing = 20
    }
    actionBox.styleClass.add("actionBox")

    private val container: VBox = new VBox {
      alignment = Pos.TopCenter
      spacing = 20
      children = List(currentPlayerName, actionBox)
    }
    container.styleClass.add("container")
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
      }
      container.children.add(actionBox)
    }
  }

  /** A factory which creates a new ActionMenu panel. */
  def apply(boardView: BoardDisplay, game: GameState, controller: InputManager): ActionMenu = new ActionMenuImpl(boardView, game, controller)
}
