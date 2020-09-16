package untitled.goose.framework.view.scalafx

import scalafx.application.Platform
import scalafx.geometry.Orientation
import scalafx.scene.Scene
import scalafx.scene.control.{SplitPane, Tab, TabPane}
import scalafx.stage.Stage
import untitled.goose.framework.controller.GameManager
import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.entities.definitions.TileIdentifier
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.view.scalafx.actionmenu.ActionMenu
import untitled.goose.framework.view.scalafx.board.{BoardDisplay, GraphicDescriptor}
import untitled.goose.framework.view.scalafx.logger.EventLogger

import scala.concurrent.Promise

/**
 * A custom scene that holds the whole game board and a TabPane containing information about the current game state and a logger.
 */
trait GameScene extends Scene {

  /** When possible, delegates the task of updating the scene with the new information regarding the board,
   * re-renders now available actions and updates the logger.
   *
   * @param state            the new game's state.
   * @param availableActions the new available action for the player in turn.
   */
  def updateScene(state: GameState, availableActions: Set[Action]): Unit

  /**
   * Closes the stage.
   */
  def close(): Unit

  /** When possible, updates the logger with the newly happened event.
   *
   * @param event the event to append to the logger.
   */
  def logEvent(event: GameEvent): Unit

  /** When possible, creates a new dialog.
   *
   * @param dialogContent the content to add to the dialog.
   * @param promise       the promise used to check the action the user has chosen, passed to the new dialog itself.
   */
  def showDialog(dialogContent: DialogContent, promise: Promise[GameEvent]): Unit
}

object GameScene {

  def apply(stage: Stage, commandSender: GameManager, gameState: GameState, graphicMap: Map[TileIdentifier, GraphicDescriptor]): GameScene =
    new GameSceneImpl(stage, commandSender, gameState, graphicMap)

  private class GameSceneImpl(stage: Stage, commandSender: GameManager, gameState: GameState, graphicMap: Map[TileIdentifier, GraphicDescriptor])
    extends GameScene {

    val boardProportion = 0.75

    val splitPane: SplitPane = new SplitPane {
      orientation = Orientation.Vertical
    }

    this.content = splitPane

    val boardView: BoardDisplay = BoardDisplay(gameState.gameBoard, graphicMap)
    splitPane.items.add(boardView)
    boardView.prefWidth <== this.width
    boardView.prefHeight <== this.height * boardProportion

    val actionMenu: ActionMenu = ActionMenu(boardView, gameState, commandSender)
    val logger: EventLogger = EventLogger(gameState)

    val tabPane = new TabPane
    val gameControlTab: Tab = new Tab {
      text = "Action Menu"
      content = actionMenu
    }
    val loggerTab: Tab = new Tab {
      text = "Logger"
      content = logger
    }
    tabPane.tabs = List(gameControlTab, loggerTab)

    tabPane.prefWidth <== this.width
    tabPane.prefHeight <== this.height * (1 - boardProportion)
    tabPane.minHeight <== this.height * (1 - boardProportion)

    splitPane.items.add(tabPane)
    splitPane.setDividerPositions(boardProportion)

    stage.setOnCloseRequest(_ => commandSender.stopGame())

    override def updateScene(state: GameState, availableActions: Set[Action]): Unit =
      Platform.runLater(() => {
        boardView.updateMatchState(state)
        actionMenu.displayActions(availableActions, state.currentPlayer)
        logger.logHistoryDiff(state)
      })

    override def close(): Unit = Platform.runLater(stage.close())

    override def showDialog(dialogContent: DialogContent, promise: Promise[GameEvent]): Unit =
      Platform.runLater(DialogUtils.launchDialog(stage, dialogContent, promise))

    override def logEvent(event: GameEvent): Unit = Platform.runLater(logger.logEvent(event))

    this.stylesheets.add("css/styleGameScene.css")
    this.stylesheets.add("css/fixedStyleGameScene.css")
  }

}
