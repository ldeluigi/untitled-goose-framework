package untitled.goose.framework.view.scalafx.board

import scalafx.beans.property.ReadOnlyDoubleProperty
import scalafx.geometry.Pos._
import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Rectangle, StrokeType}
import untitled.goose.framework.model.entities.runtime.Tile

/**
 * An object which models how a single tile is rendered.
 */
trait TileVisualization extends StackPane {

  def tile: Tile

  def text: String

  def setPiece(piece: PieceVisualization): Unit

  def removePieces(): Unit

  def rectangle: Rectangle
}

object TileVisualization {

  private class TileVisualizationImpl(val tile: Tile, parentWidth: ReadOnlyDoubleProperty,
                                      parentHeight: ReadOnlyDoubleProperty, rows: Int, cols: Int, val graphicDescriptor: Option[GraphicDescriptor]) extends TileVisualization {

    var colorToApply: Color = Color.White // default tile background -> applied if not specified by the user

    var graphics: Option[Image] = None
    var imageView: Option[ImageView] = None

    val rectangle: Rectangle = new Rectangle {
      fill = colorToApply
      strokeType = StrokeType.Inside
      width <== parentWidth / cols
      height <== width
    }
    rectangle.styleClass.add("rectangle")

    val text: String = tile.definition.name match {
      case Some(value) => value
      case None => tile.definition.number.get.toString
    }

    val label = new Label(text)
    label.styleClass.add("label")

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
        colorToApply = graphicDescriptor.color.get
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
  def apply(tile: Tile, parentWidth: ReadOnlyDoubleProperty, parentHeight: ReadOnlyDoubleProperty, rows: Int, cols: Int, graphicDescriptor: Option[GraphicDescriptor]): TileVisualization =
    new TileVisualizationImpl(tile, parentWidth, parentHeight, rows, cols, graphicDescriptor: Option[GraphicDescriptor])
}
