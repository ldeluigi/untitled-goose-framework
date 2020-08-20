package view

import engine.core.vertx.GooseEngine
import engine.events.root.GameEvent
import model.TileIdentifier
import model.actions.Action
import model.entities.DialogContent
import model.game.{Game, MutableGameState}
import scalafx.application.Platform
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.stage.Stage
import view.actionmenu.ActionMenu
import view.board.{BoardDisplay, GraphicDescriptor}
import view.logger.EventLogger

import scala.concurrent.{Future, Promise}

trait ApplicationController extends Scene {
  def resolveAction(action: Action)
}

trait GooseController {
  def update(state: MutableGameState)

  def showDialog(content: DialogContent): Future[GameEvent]

  def logEvent(event: GameEvent)

  def close(): Unit
}

object ApplicationController {

  private class ApplicationControllerImpl(stage: Stage, widthSize: Int, heightSize: Int, gameMatch: Game, graphicMap: Map[TileIdentifier, GraphicDescriptor])
    extends ApplicationController with GooseController {

    val boardProportion = 0.8
    val appBarOffset = 40
    val logHeight = 200

    val borderPane = new BorderPane()
    this.content = borderPane

    val engine: GooseEngine = GooseEngine(gameMatch, this)

    val boardView: BoardDisplay = BoardDisplay(gameMatch.board, graphicMap)
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

    override def update(state: MutableGameState): Unit = Platform.runLater(() => {
      boardView.updateMatchState(state)
      actionMenu.displayActions(engine.currentMatch.availableActions)
    })

    override def close(): Unit = Platform.runLater(() => {
      stopEngine()
      stage.close()
    })

    private def stopEngine(): Unit = engine.stop()

    override def logEvent(event: GameEvent): Unit = Platform.runLater(() => {
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

  def apply(stage: Stage, width: Int, height: Int, gameMatch: Game, graphicMap: Map[TileIdentifier, GraphicDescriptor]): ApplicationController = new
      ApplicationControllerImpl(stage, width, height, gameMatch, graphicMap)
}




