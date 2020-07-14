package view

import model.MatchState
import model.entities.board.Board
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane

trait ApplicationView {

  def setBoard(board: Board)

  def setMatchState(matchState: MatchState)
}

object ApplicationView {
  def apply(width: Int, height: Int) = ApplicationViewImpl(width, height)
}

case class ApplicationViewImpl(widthSize: Int, heightSize: Int) extends Scene with ApplicationView {

  val boardProportion = 0.8
  val appBarOffset = 40
  var currentBoard: Option[Board] = None
  var currentState: Option[MatchState] = None

  val borderPane = new BorderPane()
  this.content = borderPane

  override def setBoard(board: Board): Unit = {
    if (currentBoard.isEmpty) {
      currentBoard = Some(board)
      var boardView = BoardView(currentBoard.get)
      borderPane.center = boardView
      boardView.prefWidth <== this.width * boardProportion
      boardView.prefHeight <== this.height
    }
  }

  override def setMatchState(matchState: MatchState): Unit = {
    if (currentState.isEmpty) {
      currentState = Some(matchState)
      var actionMenu = ActionMenu()
      borderPane.right = actionMenu
      actionMenu.prefWidth <== this.width * (1 - boardProportion)
    }
  }

}



