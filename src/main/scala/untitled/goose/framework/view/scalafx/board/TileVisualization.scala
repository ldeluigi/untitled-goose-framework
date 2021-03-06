package untitled.goose.framework.view.scalafx.board

import scalafx.beans.binding.NumberBinding
import scalafx.geometry.Pos._
import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color.White
import scalafx.scene.shape.{Rectangle, StrokeType}
import scalafx.scene.text.TextAlignment
import untitled.goose.framework.model.GraphicDescriptor
import untitled.goose.framework.model.entities.runtime.Tile
import untitled.goose.framework.view.scalafx.ColorUtils

/**
 * A pane which models how a single tile is rendered.
 */
trait TileVisualization extends StackPane {

  /** Gets the current tile.
   *
   * @return the tile to get.
   */
  def tile: Tile

  /** Gets the current tile's text.
   *
   * @return the tile's text to gst.
   */
  def tileText: String

  /** Sets the position of the tile's piece on the tile itself, avoiding overlapping.
   *
   * @param piece the piece to be placed.
   */
  def setPiece(piece: PieceVisualization): Unit

  /** Remove all pieces from the tile. */
  def removePieces(): Unit

  /** Gets the rectangle holding the tile.
   *
   * @return the rectangle holding the tile.
   */
  def rectangle: Rectangle
}

object TileVisualization {

  private class TileVisualizationImpl(val tile: Tile, givenWidth: NumberBinding, val graphicDescriptor: Option[GraphicDescriptor]) extends TileVisualization {

    private var graphics: Option[Image] = None
    private var imageView: Option[ImageView] = None

    override val rectangle: Rectangle = new Rectangle {
      fill = White
      strokeType = StrokeType.Inside
      width <== givenWidth
      height <== width
    }
    rectangle.styleClass.add("rectangle")

    override val tileText: String = tile.definition.name match {
      case Some(value) => value
      case None => tile.definition.number.get.toString
    }

    private val label: Label = new Label {
      text = tileText
      alignment = Center
      textAlignment = TextAlignment.Center
      maxWidth <== rectangle.width
      wrapText = true
    }


    private def fontSize(width: Double): Double = (width * 0.15) * Math.exp(-tileText.length / 10.0) + 10

    rectangle.width.onChange((_, _, w) => {
      label.style = "-fx-font-size: " + fontSize(w.asInstanceOf[Double]).toInt + "pt"
    })

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

    override def removePieces(): Unit = {
      for (p <- pieceList) {
        this.children.remove(p)
      }
      pieceList = Nil
    }

    /** Applies a custom style to the board's tiles, based on which properties are defined into the GraphicDescriptor.
     *
     * @param graphicDescriptor the GraphicDescriptor to withdraw graphic properties to apply from.
     */
    private def applyStyle(graphicDescriptor: GraphicDescriptor): Unit = {
      if (graphicDescriptor.color.isDefined) {
        rectangle.fill = ColorUtils.getColor(graphicDescriptor.color.get)
      }

      if (graphicDescriptor.imagePath.isDefined) {
        val backgroundToApply: String = graphicDescriptor.imagePath.get
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
  def apply(tile: Tile, givenWidth: NumberBinding, graphicDescriptor: Option[GraphicDescriptor]): TileVisualization =
    new TileVisualizationImpl(tile, givenWidth, graphicDescriptor: Option[GraphicDescriptor])
}
