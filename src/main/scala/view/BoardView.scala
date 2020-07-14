package view

import model.MatchState
import model.entities.board.{Board, TileDefinition}
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
  for (t <- board.tiles) {
    var currentTile = TileVisualization(t, width, height)
    currentTile.layoutX <== currentTile.width * i
    boardPane.children.add(currentTile)
    i = i + 1
  }

  override def updateMatchState(matchState: MatchState): Unit = ???
}
