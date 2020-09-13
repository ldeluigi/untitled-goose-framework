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

trait GameScene extends Scene {

  def updateScene(state: GameState, availableActions: Set[Action]): Unit

  def close(): Unit

  def logEvent(event: GameEvent): Unit

  def showDialog(dialogContent: DialogContent, promise: Promise[GameEvent]): Unit
}

object GameScene {

  def apply(stage: Stage, commandSender: GameManager, gameMatch: GameState, graphicMap: Map[TileIdentifier, GraphicDescriptor]): GameScene =
    new GameSceneImpl(stage, commandSender, gameMatch, graphicMap)

  private class GameSceneImpl(stage: Stage, commandSender: GameManager, gameMatch: GameState, graphicMap: Map[TileIdentifier, GraphicDescriptor])
    extends GameScene {

    val boardProportion = 0.8

    val splitPane: SplitPane = new SplitPane {
      orientation = Orientation.Vertical
    }

    this.content = splitPane

    val boardView: BoardDisplay = BoardDisplay(gameMatch.gameBoard, graphicMap)
    splitPane.items.add(boardView)
    boardView.prefWidth <== this.width
    boardView.prefHeight <== this.height * boardProportion

    val actionMenu: ActionMenu = ActionMenu(boardView, gameMatch, commandSender)
    val logger: EventLogger = EventLogger(gameMatch)

    val tabPane = new TabPane
    val actionsTab: Tab = new Tab {
      text = "Action Menu"
      content = actionMenu
    }
    val loggerTab: Tab = new Tab {
      text = "Logger"
      content = logger
    }
    tabPane.tabs = List(actionsTab, loggerTab)

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
  }

}
