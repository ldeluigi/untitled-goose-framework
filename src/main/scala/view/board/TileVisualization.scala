package view.board

import model.Tile
import scalafx.beans.property.ReadOnlyDoubleProperty
import scalafx.geometry.Pos._
import scalafx.scene.control.Label
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color._
import scalafx.scene.shape.{Rectangle, StrokeType}

trait TileVisualization extends StackPane {

  def tile: Tile

  def text: String

  def setPiece(piece: PieceVisualization): Unit

  def removePieces(): Unit

  def rectangle: Rectangle
}

object TileVisualization {

  private class TileVisualizationImpl(val tile: Tile, parentWidth: ReadOnlyDoubleProperty,
                                      parentHeight: ReadOnlyDoubleProperty, rows: Int, cols: Int) extends TileVisualization {

    val strokeSize = 3

    var rectangle: Rectangle = new Rectangle {
      fill = White
      stroke = Black
      strokeType = StrokeType.Inside
      strokeWidth = strokeSize
      width <== (parentWidth / cols)
      height <== parentHeight / rows
    }
    val text: String = tile.number.get.toString

    var label = new Label(text)

    this.children.addAll(rectangle, label)

    var pieceList: List[PieceVisualization] = Nil

    override def setPiece(piece: PieceVisualization): Unit = {
      //TODO set alignment based on how many are on this tile
      pieceList.size match {
        case 0 => piece.alignment = CenterLeft
        case 1 => piece.alignment = CenterRight
        case 2 => piece.alignment = BottomCenter
        case 3 => piece.alignment = TopCenter
        case 4 => piece.alignment = TopLeft
        case 5 => piece.alignment = BottomRight
        case 6 => piece.alignment = TopRight
        case 7 => piece.alignment = BottomLeft
        case _ => piece.alignment = Center
      }
      this.children.add(piece)
      pieceList = piece :: pieceList
    }

    override def removePieces(): Unit = {
      for (p <- pieceList) {
        this.children.remove(p)
      }
      pieceList = Nil
    }

  }

  def apply(tile: Tile, parentWidth: ReadOnlyDoubleProperty, parentHeight: ReadOnlyDoubleProperty, rows: Int, cols: Int): TileVisualization =
    new TileVisualizationImpl(tile, parentWidth, parentHeight, rows, cols)
}

