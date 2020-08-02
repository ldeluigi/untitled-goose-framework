package view

import model.Tile
import scalafx.beans.property.ReadOnlyDoubleProperty
import scalafx.geometry.Pos
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

    var rectangle: Rectangle = new Rectangle {
      fill = White
      stroke = Black
      strokeType = StrokeType.Inside
      strokeWidth <== parentWidth / cols * 0.05 //TODO tweak value
      width <== parentWidth / cols
      height <== parentHeight / rows
    }
    val text: String = tile.number.get.toString

    var label = new Label(text)

    this.children.addAll(rectangle, label)

    var pieceList: List[PieceVisualization] = Nil

    override def setPiece(piece: PieceVisualization): Unit = {
      //TODO set alignment based on how many are on this tile
      piece.alignment = Pos.BottomLeft
      this.children.add(piece)
      pieceList = piece :: pieceList
    }

    override def removePieces(): Unit = {
      for (p <- pieceList) {
        this.children.remove(p)
      }
    }

  }

  def apply(tile: Tile, parentWidth: ReadOnlyDoubleProperty, parentHeight: ReadOnlyDoubleProperty, rows: Int, cols: Int): TileVisualization =
    new TileVisualizationImpl(tile, parentWidth, parentHeight, rows, cols)
}

