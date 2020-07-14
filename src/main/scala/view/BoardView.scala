package view

import model.MatchState
import model.entities.board.Board
import scalafx.scene.control.ScrollPane
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color._
import scalafx.scene.shape.{Rectangle, StrokeType}


trait BoardView {
  def updateMatchState(matchState: MatchState)
}

object BoardView {
  def apply(board: Board) = BoardViewImpl(board)
}

case class BoardViewImpl(board: Board) extends ScrollPane with BoardView {
  val boardPane = new Pane()

  boardPane.style = "-fx-background-color: #000"

  this.content = boardPane
  this.pannable = true
  this.hbarPolicy = ScrollPane.ScrollBarPolicy.Always
  this.vbarPolicy = ScrollPane.ScrollBarPolicy.Always

  //Draw tiles

  var i = 0
  for (i <- 0 to board.tiles.size) {
    var currentTile = new Rectangle {
      fill = Red
      stroke = Yellow
      strokeType = StrokeType.Inside
    }
    currentTile.strokeWidth <== this.width / 8 * 0.05
    currentTile.width <== this.width / 8
    currentTile.height <== this.height / 6
    currentTile.x <== this.width / 8 * i
    boardPane.children.add(currentTile)
  }

  override def updateMatchState(matchState: MatchState): Unit = ???
}
