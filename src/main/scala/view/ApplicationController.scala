package view

import engine.`match`.Match
import engine.core.vertx.GooseEngine
import engine.events.root.GameEvent
import model.MatchState
import model.actions.Action
import model.entities.DialogContent
import scalafx.application.Platform
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.BorderPane
import view.actionmenu.ActionMenu
import view.board.BoardDisplay
import view.logger.EventLogger

import scala.concurrent.{Future, Promise}

//TODO return scene instead of being a Scene
trait ApplicationController extends Scene {
  def resolveAction(action: Action)

  def close(): Unit
}

trait GooseController {
  def update(state: MatchState)

  def showDialog(content: DialogContent): Future[GameEvent]

  def logEvent(event: GameEvent)
}

object ApplicationController {

  private class ApplicationControllerImpl(widthSize: Int, heightSize: Int, gameMatch: Match)
    extends ApplicationController with GooseController {

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

    def resolveAction(action: Action): Unit = {
      action.execute(engine.eventSink, engine.currentMatch.currentState)
    }

    override def update(state: MatchState): Unit = Platform.runLater(() => {
      boardView.updateMatchState(state)
      actionMenu.displayActions(engine.currentMatch.availableActions)
    })

    override def close(): Unit = engine.stop()

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

  def apply(width: Int, height: Int, gameMatch: Match): ApplicationController = new
      ApplicationControllerImpl(width, height, gameMatch)
}




