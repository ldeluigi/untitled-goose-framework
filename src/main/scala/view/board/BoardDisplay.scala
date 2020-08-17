package view.board

import model.game.{GameBoard, MutableGameState}
import scalafx.scene.control.ScrollPane
import scalafx.scene.layout.Pane


trait BoardDisplay extends ScrollPane {
  def updateMatchState(matchState: MutableGameState)
}

object BoardDisplay {

  private class BoardDisplayImpl(matchBoard: GameBoard) extends BoardDisplay {
    val boardPane = new Pane()

    var tiles: List[TileVisualization] = Nil

    this.content = boardPane
    this.pannable = true
    this.hbarPolicy = ScrollPane.ScrollBarPolicy.Always
    this.vbarPolicy = ScrollPane.ScrollBarPolicy.Always

    //Draw tile
    var i = 0
    var rows: Int = matchBoard.board.disposition.rows
    var cols: Int = matchBoard.board.disposition.columns

    for (t <- matchBoard.tiles.toList.sorted) {
      val currentTile = TileVisualization(t, width, height, rows, cols)
      currentTile.layoutX <== this.width / cols * matchBoard.board.disposition.tilePlacement(i)._1
      currentTile.layoutY <== this.height / rows * matchBoard.board.disposition.tilePlacement(i)._2
      boardPane.children.add(currentTile)
      i = i + 1
      tiles = currentTile :: tiles
    }

    override def updateMatchState(matchState: MutableGameState): Unit = {
      for (t <- tiles) {
        t.removePieces()
      }
      for (p <- matchState.playerPieces) {
        val positionTile = tiles.find(v => p._2.position.isDefined && v.tile == p._2.position.get.tile)
        if (positionTile.isDefined) {
          if (p._1 == matchState.currentPlayer) {
            setFocus(positionTile.get)
          }
          positionTile.get.setPiece(PieceVisualization(p._2, this.width))
        }
      }
    }

    private def setFocus(positionTile: TileVisualization): Unit = {
      this.setHvalue(positionTile.getLayoutX / boardPane.getWidth)
      this.setVvalue(positionTile.getLayoutY / boardPane.getHeight)
    }
  }


  def apply(board: GameBoard): BoardDisplay = new BoardDisplayImpl(board)
}

