package view

import model.entities.board.TileDefinition
import scalafx.beans.property.{ReadOnlyDoubleProperty}
import scalafx.scene.control.Label
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color.{Red, Black}
import scalafx.scene.shape.{Rectangle, StrokeType}

trait TileVisualization {
  def text: String

  def rectangle: Rectangle
}

object TileVisualization {
  def apply(tile: TileDefinition, parentWidth: ReadOnlyDoubleProperty, parentHeight: ReadOnlyDoubleProperty) = new TileVisualizationImpl(tile, parentWidth, parentHeight)
}

class TileVisualizationImpl(tile: TileDefinition, parentWidth: ReadOnlyDoubleProperty, parentHeight: ReadOnlyDoubleProperty) extends StackPane with TileVisualization {
  var rectangle: Rectangle = new Rectangle {
    fill = Red
    stroke = Black
    strokeType = StrokeType.Inside
    strokeWidth <== parentWidth / 8 * 0.05
    width <== parentWidth / 8
    height <== parentHeight / 6
  }
  val text: String = tile.number.get.toString;

  var label = new Label(text)

  this.children.addAll(rectangle, label)


}
