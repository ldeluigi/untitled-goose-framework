package view.board

import model.TileIdentifier.Group
import model.game.{GameBoard, GameState}
import model.{Tile, TileIdentifier}
import scalafx.scene.control.ScrollPane
import scalafx.scene.layout.Pane

/** A custom pane that contains the game board.
 *
 */
trait BoardDisplay extends ScrollPane {
  def updateMatchState(matchState: GameState)
}

object BoardDisplay {

  private class BoardDisplayImpl(matchBoard: GameBoard, graphicMap: Map[TileIdentifier, GraphicDescriptor]) extends BoardDisplay {
    val boardPane = new Pane()

    var tiles: List[TileVisualization] = Nil

    this.content = boardPane
    this.pannable = true
    this.hbarPolicy = ScrollPane.ScrollBarPolicy.Always
    this.vbarPolicy = ScrollPane.ScrollBarPolicy.Always

    var i = 0
    val rows: Int = matchBoard.board.disposition.rows
    val cols: Int = matchBoard.board.disposition.columns

    for (tile <- matchBoard.tiles.toList.sorted) {
      renderTile(TileVisualization(tile, width, height, rows, cols, getGraphicDescriptor(tile)))
    }

    private def renderTile(currentTile: TileVisualization): Unit = {
      currentTile.layoutX <== this.width / cols * matchBoard.board.disposition.tilePlacement(i)._1
      currentTile.layoutY <== this.height / rows * matchBoard.board.disposition.tilePlacement(i)._2
      boardPane.children.add(currentTile)
      i = i + 1
      tiles = currentTile :: tiles
    }


    private def getGraphicDescriptor(tile: Tile): Option[GraphicDescriptor] = {
      var graphicSeq: Seq[GraphicDescriptor] = Seq()
      if (tile.definition.name.isDefined) {
        graphicMap.get(TileIdentifier(tile.definition.name.get))
          .foreach(g => graphicSeq = graphicSeq :+ g)
      } else if (tile.definition.number.isDefined) {
        graphicMap.get(TileIdentifier(tile.definition.number.get))
          .foreach(g => graphicSeq = graphicSeq :+ g)
      }
      for (group <- tile.definition.groups) {
        graphicMap.get(TileIdentifier(Group(group)))
          .foreach(g => graphicSeq = graphicSeq :+ g)
      }
      if (graphicSeq.nonEmpty) {
        Some(graphicSeq.reduce(GraphicDescriptor.merge))
      } else None
    }


    override def updateMatchState(matchState: GameState): Unit = {
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

  /** A factory which creates a new BoardDisplay. */
  def apply(board: GameBoard, graphicMap: Map[TileIdentifier, GraphicDescriptor]): BoardDisplay = new BoardDisplayImpl(board, graphicMap)
}

