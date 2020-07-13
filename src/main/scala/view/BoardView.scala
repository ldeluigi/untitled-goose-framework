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
  def apply(board: Board, width: Int, height: Int) = BoardViewImpl(board, width, height)
}

case class BoardViewImpl(board: Board, widthSize: Int, heightSize: Int) extends ScrollPane with BoardView {
  this.setMinSize(widthSize, heightSize)
  this.setMaxSize(widthSize, heightSize)


  val boardPane = new Pane()
  boardPane.style = "-fx-background-color: #000"

  this.content = boardPane
  this.pannable = false
  this.hbarPolicy = ScrollPane.ScrollBarPolicy.Always
  this.vbarPolicy = ScrollPane.ScrollBarPolicy.Always

  //Draw tiles

  val tileWidth = widthSize / 8
  val tileHeight = widthSize / 10
  var i = 0
  for (i <- 0 to board.tiles.size) {
    boardPane.children.add(new Rectangle {
      x = 0 + (i * tileWidth)
      y = 0
      width = tileWidth
      height = tileHeight
      fill = Red
      strokeWidth = 5
      stroke = Yellow
      strokeType = StrokeType.Inside
    })
  }

  override def updateMatchState(matchState: MatchState): Unit = ???
}
