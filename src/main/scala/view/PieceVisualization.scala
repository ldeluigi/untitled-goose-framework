package view


import scalafx.scene.layout.{Pane, StackPane}
import scalafx.scene.paint.Color._
import scalafx.scene.shape.{Circle, Shape}

trait PieceVisualization extends StackPane {
  def pieceShape: Shape
}

object PieceVisualization {
  def apply(): PieceVisualization = PieceVisualizationImpl()
}

case class PieceVisualizationImpl() extends PieceVisualization {

  val pieceShape = new Circle {
    fill = Blue
    radius = 20
  }
  this.children.addAll(pieceShape)
}
