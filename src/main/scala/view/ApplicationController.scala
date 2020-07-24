package view

import engine.`match`.Match
import engine.events.core.EventSink
import engine.vertx.GooseEngine
import model.{GameEvent, MatchState}
import model.actions.Action
import scalafx.application.Platform
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane

//TODO return scene instead of being a Scene
trait ApplicationController extends Scene {
  def resolveAction(action: Action)
  def close(): Unit
}

trait GooseController {
  def update(state: MatchState)
}

object ApplicationController {

  // TODO check wtf are this ignored parameters
  private class ApplicationControllerImpl(widthSize: Int, heightSize: Int, gameMatch: Match)
    extends ApplicationController with GooseController {

    val boardProportion = 0.8
    val appBarOffset = 40

    val borderPane = new BorderPane()
    this.content = borderPane

    val engine: GooseEngine = GooseEngine(gameMatch)

    val boardView: BoardView = BoardView(gameMatch.board)
    borderPane.center = boardView
    boardView.prefWidth <== this.width * boardProportion
    boardView.prefHeight <== this.height
    boardView.updateMatchState(gameMatch.currentState)

    val actionMenu: ActionMenu = ActionMenu(boardView, this)
    borderPane.right = actionMenu
    actionMenu.prefWidth <== this.width * (1 - boardProportion)
    actionMenu.displayActions(gameMatch.availableActions)

    def resolveAction(action: Action): Unit = {
      action.execute(engine.eventSink, engine.currentMatch)
    }

    override def update(state: MatchState): Unit = Platform.runLater(() =>  {
      // TODO consider grouping inside a  method
      boardView.updateMatchState(state)
      actionMenu.displayActions(engine.currentMatch.availableActions)
    })

    override def close(): Unit = engine.stop()
  }

  def apply(width: Int, height: Int, gameMatch: Match): ApplicationController = new
      ApplicationControllerImpl(width, height, gameMatch)
}




