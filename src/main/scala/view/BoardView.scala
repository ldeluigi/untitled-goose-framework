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

  var tiles: List[TileVisualization] = Nil

  this.content = boardPane
  this.pannable = true
  this.hbarPolicy = ScrollPane.ScrollBarPolicy.Always
  this.vbarPolicy = ScrollPane.ScrollBarPolicy.Always

  //Draw tiles
  var i = 0
  var rows = 6
  var cols = 8

  for (t <- board.tiles) {
    val currentTile = TileVisualization(t, width, height, rows, cols)
    currentTile.layoutX <== this.width / cols * i
    boardPane.children.add(currentTile)
    i = i + 1
    tiles = currentTile :: tiles
  }


  override def updateMatchState(matchState: MatchState): Unit = {
    for (p <- matchState.playerPieces) {
      val positionTile = tiles.find(v => v.tile == p._2.position.tile)
      if (positionTile.isDefined) {
        positionTile.get.setPiece(PieceVisualization())
      }
    }
  }
}
