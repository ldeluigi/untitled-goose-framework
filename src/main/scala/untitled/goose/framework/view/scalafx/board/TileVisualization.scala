package untitled.goose.framework.view.scalafx.board

import scalafx.beans.binding.NumberBinding
import scalafx.beans.property.ReadOnlyDoubleProperty
import scalafx.geometry.Pos._
import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color.White
import scalafx.scene.shape.{Rectangle, StrokeType}
import scalafx.scene.text.TextAlignment
import untitled.goose.framework.model.entities.runtime.Tile

/**
 * An object which models how a single tile is rendered.
 */
trait TileVisualization extends StackPane {

  def tile: Tile

  def tileText: String

  def setPiece(piece: PieceVisualization): Unit

  def removePieces(): Unit

  def rectangle: Rectangle
}

object TileVisualization {

  //TODO take width property as constructur parameter and use that
  private class TileVisualizationImpl(val tile: Tile, givenWidth: NumberBinding, val graphicDescriptor: Option[GraphicDescriptor]) extends TileVisualization {

    var graphics: Option[Image] = None
    var imageView: Option[ImageView] = None

    val rectangle: Rectangle = new Rectangle {
      fill = White
      strokeType = StrokeType.Inside
      width <== givenWidth
      height <== width
    }
    rectangle.styleClass.add("rectangle")

    val tileText: String = tile.definition.name match {
      case Some(value) => value
      case None => tile.definition.number.get.toString
    }


    val label: Label = new Label {
      text = tileText
      alignment = Center
      textAlignment = TextAlignment.Center
      maxWidth <== rectangle.width
      wrapText = true
    }

    def fontSize(w: Double): Double = (w * 0.15) * Math.exp(-tileText.length / 10.0) + 10

    rectangle.width.onChange((_, _, w) => {
      label.style = "-fx-font-size: " + fontSize(w.asInstanceOf[Double]).toInt + "pt"
    })


    //val isMaxWidth: BooleanBinding = label.width > rectangle.width / 2
    //label.style <== when(isMaxWidth) choose "-fx-font: bold 10pt Arial; -fx-" otherwise "-fx-font: bold 15pt Arial"
    label.styleClass.add("tileLabel")


    graphicDescriptor.foreach(applyStyle)

    // to stack things up correctly, add the rectangle itself and the label, then add the image if present
    this.children.addAll(rectangle)
    if (imageView.isDefined) {
      this.children.add(imageView.get)
    } else {
      this.children.add(label)
    }

    var pieceList: List[PieceVisualization] = Nil

    /** Sets the position of the tile's piece on the piece itself.
     *
     * @param piece the piece to be placed.
     */
    override def setPiece(piece: PieceVisualization): Unit = {
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

    /** Remove all pieces from the tile. */
    override def removePieces(): Unit = {
      for (p <- pieceList) {
        this.children.remove(p)
      }
      pieceList = Nil
    }


    private def applyStyle(graphicDescriptor: GraphicDescriptor): Unit = {
      if (graphicDescriptor.color.isDefined) {
        rectangle.fill = graphicDescriptor.color.get
      }

      if (graphicDescriptor.path.isDefined) {
        val backgroundToApply: String = graphicDescriptor.path.get
        graphics = Some(new Image(backgroundToApply))
        imageView = Some(new ImageView {
          image = graphics.get
          preserveRatio = true
          fitWidth <== rectangle.width - rectangle.strokeWidth * 2
          fitHeight <== rectangle.height - rectangle.strokeWidth * 2
        })
      }
    }
  }

  /** A factory used to render a new Tile, given the tile itself, its parent and panel dimension and the graphic properties that need to be set. */
  def apply(tile: Tile, givenWidth: NumberBinding, parentHeight: ReadOnlyDoubleProperty, rows: Int, cols: Int, graphicDescriptor: Option[GraphicDescriptor]): TileVisualization =
    new TileVisualizationImpl(tile, givenWidth, graphicDescriptor: Option[GraphicDescriptor])
}
