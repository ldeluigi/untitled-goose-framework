package view.scalafx

import controller.CommandSender
import model.TileIdentifier
import model.actions.Action
import model.entities.DialogContent
import model.entities.runtime.{Game, GameState}
import model.events.GameEvent
import scalafx.application.Platform
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.stage.Stage
import view.scalafx.actionmenu.ActionMenu
import view.scalafx.board.{BoardDisplay, GraphicDescriptor}
import view.scalafx.logger.EventLogger

import scala.concurrent.Promise

trait GameScene extends Scene {

  def updateScene(state: GameState, availableActions: Set[Action]): Unit

  def close(): Unit

  def logEvent(event: GameEvent): Unit

  def showDialog(dialogContent: DialogContent, promise: Promise[GameEvent]): Unit
}

object GameScene {

  def apply(stage: Stage, commandSender: CommandSender, gameMatch: Game, graphicMap: Map[TileIdentifier, GraphicDescriptor]): GameScene =
    new GameSceneImpl(stage, commandSender, gameMatch, graphicMap)

  private class GameSceneImpl(stage: Stage, commandSender: CommandSender, gameMatch: Game, graphicMap: Map[TileIdentifier, GraphicDescriptor])
    extends GameScene {

    val boardProportion = 0.8
    val logHeight = 200

    val borderPane = new BorderPane()
    this.content = borderPane

    val boardView: BoardDisplay = BoardDisplay(gameMatch.board, graphicMap)
    borderPane.center = boardView
    boardView.prefWidth <== this.width * boardProportion
    boardView.prefHeight <== this.height - logHeight

    val actionMenu: ActionMenu = ActionMenu(boardView, commandSender)
    borderPane.right = actionMenu
    actionMenu.prefWidth <== this.width * (1 - boardProportion)

    val logger: EventLogger = EventLogger(gameMatch.currentState, logHeight)
    borderPane.bottom = logger
    logger.prefWidth <== this.width

    //Init the view
    updateScene(gameMatch.currentState, gameMatch.availableActions)

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
  }

}
