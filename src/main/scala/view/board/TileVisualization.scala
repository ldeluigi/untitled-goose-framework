package view.board

import model.Tile
import scalafx.beans.property.ReadOnlyDoubleProperty
import scalafx.geometry.Pos._
import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Rectangle, StrokeType}

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
    val strokeColor: Color = Color.Black // default stroke color -> applied if not specified by the user
    val strokeSize = 3

    var image: Image = _
    var imageContainer: ImageView = _
    var imageFlag: Boolean = false

    graphicDescriptor.foreach(applyStyle)

    val rectangle: Rectangle = new Rectangle {
      fill = colorToApply
      stroke = strokeColor
      strokeType = StrokeType.Inside
      strokeWidth = strokeSize
      width <== parentWidth / cols
      height <== parentHeight / rows
    }

    val text: String = tile.definition.name match {
      case Some(value) => value
      case None => tile.definition.number.get.toString
    }

    val label = new Label(text)

    // to stack things up correctly, add the rectangle itself and the label, then add the image if present
    this.children.addAll(rectangle, label)
    if (imageFlag) {
      this.children.add(imageContainer)
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
        image = new Image(backgroundToApply, parentWidth.getValue / cols, parentHeight.getValue / rows, true, true)
        imageContainer = new ImageView(backgroundToApply)
        imageFlag = true
      }
    }
  }

  /** A factory used to render a new Tile, given the tile itself, its parent and panel dimension and the graphic properties that need to be set. */
  def apply(tile: Tile, parentWidth: ReadOnlyDoubleProperty, parentHeight: ReadOnlyDoubleProperty, rows: Int, cols: Int, graphicDescriptor: Option[GraphicDescriptor]): TileVisualization =
    new TileVisualizationImpl(tile, parentWidth, parentHeight, rows, cols, graphicDescriptor: Option[GraphicDescriptor])
}

