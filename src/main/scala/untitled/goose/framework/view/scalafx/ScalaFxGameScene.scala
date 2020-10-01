package untitled.goose.framework.view.scalafx

import scalafx.application.Platform
import scalafx.geometry.Orientation
import scalafx.scene.Scene
import scalafx.scene.control.{SplitPane, Tab, TabPane}
import scalafx.scene.input.KeyCode
import scalafx.stage.Stage
import untitled.goose.framework.model.GraphicDescriptor
import untitled.goose.framework.model.actions.Action
import untitled.goose.framework.model.entities.DialogContent
import untitled.goose.framework.model.entities.definitions.TileIdentifier
import untitled.goose.framework.model.entities.runtime.GameState
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.view.InputManager
import untitled.goose.framework.view.scalafx.actionmenu.ActionMenu
import untitled.goose.framework.view.scalafx.board.BoardDisplay
import untitled.goose.framework.view.scalafx.logger.EventLogger
import untitled.goose.framework.view.scene.GameScene

import scala.concurrent.Promise

/** A ScalaFxGameScene is a GameScene which uses ScalaFx as graphic library. */
trait ScalaFxGameScene extends Scene with GameScene

object ScalaFxGameScene {

  /**
   * Factory method to create a new ScalaFxGameScene.
   *
   * @param stage         the scalafx.scene.Scene containing the graphic components.
   * @param commandSender the input manager for user actions.
   * @param gameState     the starting game state, that allows the drawing of the first board.
   * @param graphicMap    the graphic representation of tiles.
   * @return a new ScalaFxGameScene.
   */
  def apply(stage: Stage,
            commandSender: InputManager,
            gameState: GameState,
            graphicMap: Map[TileIdentifier, GraphicDescriptor]): ScalaFxGameScene =
    new ScalaFxGameSceneImpl(stage, commandSender, gameState, graphicMap)

  private class ScalaFxGameSceneImpl(stage: Stage, commandSender: InputManager, gameState: GameState, graphicMap: Map[TileIdentifier, GraphicDescriptor])
    extends ScalaFxGameScene {

    private val boardProportion = 0.75

    private val splitPane: SplitPane = new SplitPane {
      orientation = Orientation.Vertical
    }

    this.content = splitPane

    private val boardView: BoardDisplay = BoardDisplay(gameState.gameBoard, graphicMap)
    onKeyPressed = e => {
      if (e.getCode == KeyCode.Plus.delegate) boardView.zoomIn()
      if (e.getCode == KeyCode.Minus.delegate) boardView.zoomOut()
    }
    splitPane.items.add(boardView)
    boardView.prefWidth <== this.width
    boardView.prefHeight <== this.height * boardProportion

    private val actionMenu: ActionMenu = ActionMenu(boardView, gameState, commandSender)
    private val logger: EventLogger = EventLogger(gameState)

    private val tabPane = new TabPane
    private val gameControlTab: Tab = new Tab {
      text = "Action Menu"
      content = actionMenu
      closable = false
    }
    private val loggerTab: Tab = new Tab {
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
