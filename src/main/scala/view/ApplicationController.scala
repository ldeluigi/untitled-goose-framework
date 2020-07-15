package view

import engine.`match`.Match
import model.actions.Action
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane

trait ApplicationController extends Scene {
  def resolveAction(action: Action)
}

object ApplicationController {
  def apply(width: Int, height: Int, gameMatch: Match): ApplicationController =
    ApplicationControllerImpl(width, height, gameMatch)
}

case class ApplicationControllerImpl(widthSize: Int, heightSize: Int, gameMatch: Match) extends ApplicationController {

  val boardProportion = 0.8
  val appBarOffset = 40

  val borderPane = new BorderPane()
  this.content = borderPane

  val boardView: BoardView = BoardView(gameMatch.board)
  borderPane.center = boardView
  boardView.prefWidth <== this.width * boardProportion
  boardView.prefHeight <== this.height

  val actionMenu: ActionMenu = ActionMenu(boardView, this)
  borderPane.right = actionMenu
  actionMenu.prefWidth <== this.width * (1 - boardProportion)
  actionMenu.displayActions(gameMatch.availableActions)

  override def resolveAction(action: Action): Unit = {
    boardView.updateMatchState(gameMatch.resolveAction(action))
    actionMenu.displayActions(gameMatch.availableActions)
  }

}



