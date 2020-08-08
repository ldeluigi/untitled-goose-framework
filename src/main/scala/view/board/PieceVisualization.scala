package view.board

import model.entities.board.Piece
import scalafx.beans.property.ReadOnlyDoubleProperty
import scalafx.scene.layout.StackPane
import scalafx.scene.shape.{Circle, Shape}
import view.ColorUtils

trait PieceVisualization extends StackPane {
  def pieceShape: Shape
}

object PieceVisualization {

  private class PieceVisualizationImpl(piece: Piece, parentWidth: ReadOnlyDoubleProperty) extends PieceVisualization {

    val pieceShape: Shape = new Circle {
      fill = ColorUtils.getColor(piece.color)
      radius <== parentWidth * 0.012
    }
    this.children.addAll(pieceShape)
  }

  def apply(piece: Piece, parentWidth: ReadOnlyDoubleProperty): PieceVisualization = new PieceVisualizationImpl(piece, parentWidth)
}

