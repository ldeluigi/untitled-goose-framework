package view

import model.MatchState
import model.entities.board.Board
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane

trait ApplicationView {
  def setMatchState(matchState: MatchState)
}

object ApplicationView {
  def apply(width: Int, height: Int, board: Board) = ApplicationViewImpl(width, height, board)
}

case class ApplicationViewImpl(widthSize: Int, heightSize: Int, board: Board) extends Scene with ApplicationView {

  val boardProportion = 0.8
  val appBarOffset = 40
  var currentState: Option[MatchState] = None

  val borderPane = new BorderPane()
  this.content = borderPane

  val boardView = BoardView(board)
  borderPane.center = boardView
  boardView.prefWidth <== this.width * boardProportion
  boardView.prefHeight <== this.height

  val actionMenu = ActionMenu(boardView)
  borderPane.right = actionMenu
  actionMenu.prefWidth <== this.width * (1 - boardProportion)

  override def setMatchState(matchState: MatchState): Unit = {
    if (currentState.isEmpty) {
      currentState = Some(matchState)
    }
  }

}



