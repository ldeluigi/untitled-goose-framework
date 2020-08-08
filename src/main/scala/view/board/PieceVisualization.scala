package view.board

import model.entities.board.Piece
import scalafx.scene.layout.StackPane
import scalafx.scene.shape.{Circle, Shape}
import view.ColorUtils

trait PieceVisualization extends StackPane {
  def pieceShape: Shape
}

object PieceVisualization {

  private class PieceVisualizationImpl(piece: Piece) extends PieceVisualization {

    val pieceShape: Shape = new Circle {
      fill = ColorUtils.getColor(piece.color)
      radius = 20
    }
    this.children.addAll(pieceShape)
  }

  def apply(piece: Piece): PieceVisualization = new PieceVisualizationImpl(piece)
}

