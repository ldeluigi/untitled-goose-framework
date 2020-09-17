package untitled.goose.framework.view.scalafx.board

import javafx.beans.property.SimpleIntegerProperty
import scalafx.beans.binding.NumberBinding
import scalafx.scene.Group
import scalafx.scene.control.ScrollPane
import untitled.goose.framework.model.entities.definitions.TileIdentifier
import untitled.goose.framework.model.entities.definitions.TileIdentifier.{Group => TileGroup}
import untitled.goose.framework.model.entities.runtime.Tile._
import untitled.goose.framework.model.entities.runtime.{Board, GameState, Tile}
import untitled.goose.framework.view.GraphicDescriptor

/** A custom pane that contains the runtime definition of the board.
 *
 */
trait BoardDisplay extends ScrollPane {

  /** Updates both the focus on which a player has landed and the renders the corresponding tile onto it.
   *
   * @param matchState the game state to update from which withdraw the needed information about tiles and players.
   */
  def updateMatchState(matchState: GameState)

  def zoomIn(): Unit

  def zoomOut(): Unit
}

object BoardDisplay {

  private class BoardDisplayImpl(matchBoard: Board, graphicMap: Map[TileIdentifier, GraphicDescriptor]) extends BoardDisplay {

    val boardPane = new Group()

    var tiles: List[TileVisualization] = Nil

    var i = 0
    val rows: Int = matchBoard.definition.disposition.rows
    val cols: Int = matchBoard.definition.disposition.columns

    val widthDivider = new SimpleIntegerProperty(cols)
    val currentTileWidth: NumberBinding = width / widthDivider

    this.content = boardPane
    renderBoard()

    this.setVvalue(1)

    /** Renders the board, creating the specified number of tiles a setting a custom dimension to them,
     * while carrying the custom graphic properties.
     */
    private def renderBoard(): Unit = {
      for (tile <- matchBoard.tiles.toList.sorted)
        renderTile(TileVisualization(tile, currentTileWidth, getGraphicDescriptor(tile)))
    }

    /** Renders a single tile, setting its coordinates and adding it to the board panel.
     *
     * @param currentTile the tile to render.
     */
    private def renderTile(currentTile: TileVisualization): Unit = {
      currentTile.layoutX <== currentTile.width * matchBoard.definition.disposition(i)._1
      currentTile.layoutY <== currentTile.height * matchBoard.definition.disposition(i)._2
      boardPane.children.add(currentTile)
      i = i + 1
      tiles = currentTile :: tiles
    }

    /** Creates a GraphicDescriptor if the tile's name, number or group are defined.
     *
     * @param tile the tile to analyze and from which to withdraw graphic information from.
     * @return the GraphicDescriptor containing custom graphic properties.
     */
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

    /** Sets the board to be able to place follow on which tile the player has landed.
     *
     * @param positionTile the tile on which to set the focus.
     */
    private def setFocus(positionTile: TileVisualization): Unit = {
      val tileOffset = 1.5
      this.setHvalue((positionTile.getLayoutX * tileOffset) / this.getWidth)
      this.setVvalue((positionTile.getLayoutY * tileOffset) / this.getHeight)
    }

    override def zoomIn(): Unit = {
      if (widthDivider.get() > 1) {
        widthDivider.set(widthDivider.get() - 1)
      }
    }

    override def zoomOut(): Unit = {
      if (currentTileWidth().doubleValue() > 90) {
        widthDivider.set(widthDivider.get() + 1)
      }
    }
  }

  /** A factory which creates a new BoardDisplay. */
  def apply(board: Board, graphicMap: Map[TileIdentifier, GraphicDescriptor]): BoardDisplay = new BoardDisplayImpl(board, graphicMap)
}

