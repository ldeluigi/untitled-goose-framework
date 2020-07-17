package view

import engine.`match`.MatchBoard
import model.MatchState
import scalafx.scene.control.ScrollPane
import scalafx.scene.layout.Pane


trait BoardView extends ScrollPane {
  def updateMatchState(matchState: MatchState)
}

object BoardView {

  private class BoardViewImpl(matchBoard: MatchBoard) extends BoardView {
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

    for (t <- matchBoard.tiles.toList.sorted) {
      val currentTile = TileVisualization(t, width, height, rows, cols)
      currentTile.layoutX <== this.width / cols * i
      boardPane.children.add(currentTile)
      i = i + 1
      tiles = currentTile :: tiles
    }

    override def updateMatchState(matchState: MatchState): Unit = {
      for (t <- tiles) {
        t.removePieces()
      }
      for (p <- matchState.playerPieces) {
        val positionTile = tiles.find(v => v.tile == p._2.position.tile)
        if (positionTile.isDefined) {
          if (p._1 == matchState.currentPlayer) {
            setFocus(positionTile.get)
          }
          positionTile.get.setPiece(PieceVisualization())
        }
      }
    }

    //TODO da migliorare introduce un mini bug grafico
    private def setFocus(positionTile: TileVisualization): Unit = {
      this.setHvalue(positionTile.getLayoutX / boardPane.getWidth)
      this.setVvalue(positionTile.getLayoutY / boardPane.getHeight)
    }
  }


  def apply(board: MatchBoard): BoardView = new BoardViewImpl(board)
}

