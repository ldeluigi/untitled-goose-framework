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

  var currentBoard: Option[Board] = None
  var currentState: Option[MatchState] = None

  val borderPane = new BorderPane()
  this.content = borderPane

  override def setBoard(board: Board): Unit = {
    if (currentBoard.isEmpty) {
      currentBoard = Some(board)
      borderPane.center = BoardView(currentBoard.get, (widthSize * boardProportion).toInt, heightSize)
    }
  }

  override def setMatchState(matchState: MatchState): Unit = {
    if (currentState.isEmpty) {
      currentState = Some(matchState)
      borderPane.right = ActionMenu((widthSize * (1 - boardProportion)).toInt, heightSize)
    }
  }

}



