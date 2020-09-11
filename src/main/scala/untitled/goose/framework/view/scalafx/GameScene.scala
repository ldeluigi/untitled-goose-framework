package untitled.goose.framework.view.scalafx

import scalafx.application.Platform
import scalafx.scene.Scene
import scalafx.scene.control.{Tab, TabPane}
import scalafx.scene.layout.BorderPane
import scalafx.stage.Stage
import untitled.goose.framework.controller.GameManager
import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.entities.definitions.TileIdentifier
import untitled.goose.framework.model.entities.runtime.{Game, GameState}
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
    val logHeight = 160

    val borderPane = new BorderPane()
    this.content = borderPane

    val boardView: BoardDisplay = BoardDisplay(gameMatch.gameBoard, graphicMap)
    borderPane.center = boardView
    boardView.prefWidth <== this.width * boardProportion
    boardView.prefHeight <== this.height - logHeight

    val actionMenu: ActionMenu = ActionMenu(boardView, gameMatch, commandSender)
    actionMenu.prefWidth <== this.width * (1 - boardProportion)

    val logger: EventLogger = EventLogger(gameMatch, logHeight)
    logger.prefWidth <== this.width

    val tabPane = new TabPane
    val actionsTab = new Tab
    val loggerTab = new Tab
    actionsTab.text = "Action Menu"
    loggerTab.text = "Logger"
    actionsTab.content = actionMenu
    loggerTab.content = logger
    tabPane.tabs = List(actionsTab, loggerTab)

    borderPane.bottom = tabPane

    stage.setOnCloseRequest(_ => commandSender.stopGame())

    override def updateScene(state: GameState, availableActions: Set[Action]): Unit =
      Platform.runLater(() => {
        boardView.updateMatchState(state)
        actionMenu.displayActions(availableActions)
        logger.logHistoryDiff(state)
      })

    override def close(): Unit = Platform.runLater(stage.close())

    override def showDialog(dialogContent: DialogContent, promise: Promise[GameEvent]): Unit =
      Platform.runLater(DialogUtils.launchDialog(dialogContent, promise))

    override def logEvent(event: GameEvent): Unit = Platform.runLater(logger.logEvent(event))

    this.stylesheets.add("css/styleGameScene.css")
  }

}
