package untitled.goose.framework.view.scalafx

import scalafx.application.Platform
import scalafx.geometry.Orientation
import scalafx.scene.Scene
import scalafx.scene.control.{SplitPane, Tab, TabPane}
import scalafx.scene.input.KeyCode
import scalafx.stage.Stage
import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.entities.definitions.TileIdentifier
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.view.scalafx.actionmenu.ActionMenu
import untitled.goose.framework.view.scalafx.board.BoardDisplay
import untitled.goose.framework.view.scalafx.logger.EventLogger
import untitled.goose.framework.view.scene.GameScene
import untitled.goose.framework.view.{GraphicDescriptor, InputManager}

import scala.concurrent.Promise

// TODO scaladoc
trait ScalaFxGameScene extends Scene with GameScene

object ScalaFxGameScene {

  // TODO scaladoc
  def apply(stage: Stage,
            commandSender: InputManager,
            gameState: GameState,
            graphicMap: Map[TileIdentifier, GraphicDescriptor]): ScalaFxGameScene =
    new ScalaFxGameSceneImpl(stage, commandSender, gameState, graphicMap)

  private class ScalaFxGameSceneImpl(stage: Stage, commandSender: InputManager, gameState: GameState, graphicMap: Map[TileIdentifier, GraphicDescriptor])
    extends ScalaFxGameScene {

    val boardProportion = 0.75

    val splitPane: SplitPane = new SplitPane {
      orientation = Orientation.Vertical
    }

    this.content = splitPane

    val boardView: BoardDisplay = BoardDisplay(gameState.gameBoard, graphicMap)
    onKeyPressed = e => {
      if (e.getCode == KeyCode.Plus.delegate) boardView.zoomIn()
      if (e.getCode == KeyCode.Minus.delegate) boardView.zoomOut()
    }
    splitPane.items.add(boardView)
    boardView.prefWidth <== this.width
    boardView.prefHeight <== this.height * boardProportion

    val actionMenu: ActionMenu = ActionMenu(boardView, gameState, commandSender)
    val logger: EventLogger = EventLogger(gameState)

    val tabPane = new TabPane
    val gameControlTab: Tab = new Tab {
      text = "Action Menu"
      content = actionMenu
      closable = false
    }
    val loggerTab: Tab = new Tab {
      text = "Logger"
      content = logger
      closable = false
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
