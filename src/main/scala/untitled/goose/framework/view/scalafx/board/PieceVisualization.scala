package untitled.goose.framework.view.scalafx.board

import scalafx.beans.property.ReadOnlyDoubleProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.layout.StackPane
import scalafx.scene.shape.{Circle, Shape}
import untitled.goose.framework.model.entities.runtime.Piece
import untitled.goose.framework.view.scalafx.ColorUtils

/**
 * A panel which models how a single piece is rendered onto a Tile.
 */
trait PieceVisualization extends StackPane {

  /** Defines the shape of the piece by rendering a Circle object and filling it with the piece's previously specified color.
   *
   * @return a new Circle Shape object representing the Piece itself.
   */
  def pieceShape: Shape
}

object PieceVisualization {

  private class PieceVisualizationImpl(piece: Piece, parentWidth: ReadOnlyDoubleProperty) extends PieceVisualization {

    val pieceShape: Shape = new Circle {
      fill = ColorUtils.getColor(piece.color)
      radius <== parentWidth * 0.012
      styleClass = ObservableBuffer("pieceShape")
    }

    this.children.addAll(pieceShape)
  }

  /** A factory which renders a new Piece given the piece itself and the parent width to fit it properly. */
  def apply(piece: Piece, parentWidth: ReadOnlyDoubleProperty): PieceVisualization = new PieceVisualizationImpl(piece, parentWidth)
}

