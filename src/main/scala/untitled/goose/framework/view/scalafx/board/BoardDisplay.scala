package untitled.goose.framework.view.scalafx.board

import scalafx.scene.Group
import scalafx.scene.control.ScrollPane
import untitled.goose.framework.model.entities.definitions.TileIdentifier
import untitled.goose.framework.model.entities.definitions.TileIdentifier.{Group => TileGroup}
import untitled.goose.framework.model.entities.runtime.{Board, GameState, Tile}

/** A custom pane that contains the runtime definition.
 *
 */
trait BoardDisplay extends ScrollPane {
  def updateMatchState(matchState: GameState)
}

object BoardDisplay {

  private class BoardDisplayImpl(matchBoard: Board, graphicMap: Map[TileIdentifier, GraphicDescriptor]) extends BoardDisplay {

    val boardPane = new Group()

    var tiles: List[TileVisualization] = Nil

    var i = 0
    val rows: Int = matchBoard.definition.disposition.rows
    val cols: Int = matchBoard.definition.disposition.columns

    this.content = boardPane
    renderBoard()

    this.setVvalue(1)

    //TODO call this when resizing and take a "zoom" parameter to compute width of tiles link
    private def renderBoard(): Unit = {
      import untitled.goose.framework.model.entities.runtime.Tile._
      for (tile <- matchBoard.tiles.toList.sorted)
        renderTile(TileVisualization(tile, width / cols, height, rows, cols, getGraphicDescriptor(tile)))
    }

    private def renderTile(currentTile: TileVisualization): Unit = {
      currentTile.layoutX <== currentTile.width * matchBoard.definition.disposition(i)._1
      currentTile.layoutY <== currentTile.height * matchBoard.definition.disposition(i)._2
      boardPane.children.add(currentTile)
      i = i + 1
      tiles = currentTile :: tiles
    }

    private def getGraphicDescriptor(tile: Tile): Option[GraphicDescriptor] = {
      var graphicSeq: Seq[GraphicDescriptor] = Seq()
      if (tile.definition.name.isDefined) {
        graphicMap.get(TileIdentifier(tile.definition.name.get))
          .foreach(g => graphicSeq = graphicSeq :+ g)
      }
      if (tile.definition.number.isDefined) {
        graphicMap.get(TileIdentifier(tile.definition.number.get))
          .foreach(g => graphicSeq = graphicSeq :+ g)
      }
      tile.definition.groups.foreach { group =>
        graphicMap.get(TileIdentifier(TileGroup(group)))
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
      val tileOffset = 1.5
      this.setHvalue((positionTile.getLayoutX * tileOffset) / this.getWidth)
      this.setVvalue((positionTile.getLayoutY * tileOffset) / this.getHeight)
    }
  }

  /** A factory which creates a new BoardDisplay. */
  def apply(board: Board, graphicMap: Map[TileIdentifier, GraphicDescriptor]): BoardDisplay = new BoardDisplayImpl(board, graphicMap)
}

