package view

import engine.core.vertx.GooseEngine
import engine.events.GameEvent
import model.actions.Action
import model.entities.DialogContent
import model.game.{Game, GameState}
import scalafx.application.Platform
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.stage.Stage
import view.actionmenu.ActionMenu
import view.board.BoardDisplay
import view.logger.EventLogger

import scala.concurrent.{Future, Promise}

trait ApplicationController extends Scene {
  def resolveAction(action: Action)
}

trait GooseController {
  def update(state: GameState)

  def showDialog(content: DialogContent): Future[GameEvent]

  def logAsyncEvent(event: GameEvent)

  def close(): Unit
}

object ApplicationController {

  private class ApplicationControllerImpl(stage: Stage, widthSize: Int, heightSize: Int, gameMatch: Game)
    extends ApplicationController with GooseController {

    var previousState: GameState = gameMatch.currentState
    val boardProportion = 0.8
    val appBarOffset = 40
    val logHeight = 200

    val borderPane = new BorderPane()
    this.content = borderPane

    val engine: GooseEngine = GooseEngine(gameMatch, this)

    val boardView: BoardDisplay = BoardDisplay(gameMatch.board)
    borderPane.center = boardView
    boardView.prefWidth <== this.width * boardProportion
    boardView.prefHeight <== this.height - logHeight
    boardView.updateMatchState(gameMatch.currentState)

    val actionMenu: ActionMenu = ActionMenu(boardView, this)
    borderPane.right = actionMenu
    actionMenu.prefWidth <== this.width * (1 - boardProportion)
    actionMenu.displayActions(gameMatch.availableActions)


    val logger: EventLogger = EventLogger(logHeight)
    borderPane.bottom = logger
    logger.prefWidth <== this.width
    /*
    val showLogger: Button = new Button("logger")
    showLogger.onMouseClicked = _ => logger.setVisible(!logger.isVisible)
    borderPane.left = showLogger
     */

    stage.setOnCloseRequest(_ => stopEngine())

    def resolveAction(action: Action): Unit = {
      action.execute(engine.eventSink, engine.currentMatch.currentState)
    }

    override def update(state: GameState): Unit = Platform.runLater(() => {
      boardView.updateMatchState(state)
      actionMenu.displayActions(engine.currentMatch.availableActions)
      logHistoryDiff(state)
    })

    private def logHistoryDiff(state: GameState): Unit = {
      (state.consumableBuffer.diff(previousState.consumableBuffer) ++
        state.players.flatMap(_.history).diff(previousState.players.flatMap(_.history)) ++
        state.gameBoard.tiles.flatMap(_.history).diff(previousState.gameBoard.tiles.flatMap(_.history)))
        .foreach(logger.logEvent)
      previousState = state
    }

    override def close(): Unit = Platform.runLater(() => {
      stopEngine()
      stage.close()
    })

    private def stopEngine(): Unit = engine.stop()

    override def logAsyncEvent(event: GameEvent): Unit = Platform.runLater(() => {
      logger.logEvent(event)
    })


    override def showDialog(content: DialogContent): Future[GameEvent] = {
      val promise: Promise[GameEvent] = Promise()
      Platform.runLater(() => {
        DialogUtils.launchDialog(content, promise)
      })
      promise.future
    }
  }

  def apply(stage: Stage, width: Int, height: Int, gameMatch: Game): ApplicationController = new
      ApplicationControllerImpl(stage, width, height, gameMatch)
}




