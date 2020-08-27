package view.board

import model.TileIdentifier
import model.TileIdentifier.Group
import model.game.{GameBoard, GameState}
import scalafx.scene.control.ScrollPane
import scalafx.scene.layout.Pane

import scala.util.control.Breaks.break

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

    //Draw tile
    var i = 0
    var rows: Int = matchBoard.board.disposition.rows
    var cols: Int = matchBoard.board.disposition.columns

    for (tile <- matchBoard.tiles.toList.sorted) {


      if (tile.definition.name.isDefined) {
        if (graphicMap.contains(TileIdentifier(tile.definition.name.get))) {
          val descriptor = graphicMap.get(TileIdentifier(tile.definition.name.get))
          val currentTile = TileVisualization(tile, width, height, rows, cols, descriptor)
          styleAndRenderTile(currentTile, descriptor)
        }

      } else if (tile.definition.number.isDefined) {
        if (graphicMap.contains(TileIdentifier(tile.definition.number.get))) {
          val descriptor = graphicMap.get(TileIdentifier(tile.definition.number.get))
          val currentTile = TileVisualization(tile, width, height, rows, cols, descriptor)
          styleAndRenderTile(currentTile, descriptor)
        }

      } else if (tile.definition.groups.nonEmpty) {
        for (group <- tile.definition.groups) {
          if (graphicMap.contains(TileIdentifier(Group(group)))) {
            val descriptor = graphicMap.get(TileIdentifier(Group(group)))
            val currentTile = TileVisualization(tile, width, height, rows, cols, descriptor)
            styleAndRenderTile(currentTile, descriptor)
            break
          }
        }
      } else {
        val currentTile = TileVisualization(tile, width, height, rows, cols, None)
      }
    }

    private def styleAndRenderTile(currentTile: TileVisualization, descriptor: Option[GraphicDescriptor]): Unit = {
      currentTile.applyStyle(descriptor.get)
      //println(descriptor.get.color.toString) -> CORRETTO: CONTIENE IL VERDE SPECIFICATO NEL MAIN
      currentTile.layoutX <== this.width / cols * matchBoard.board.disposition.tilePlacement(i)._1
      currentTile.layoutY <== this.height / rows * matchBoard.board.disposition.tilePlacement(i)._2
      boardPane.children.add(currentTile)
      i = i + 1
      tiles = currentTile :: tiles
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

