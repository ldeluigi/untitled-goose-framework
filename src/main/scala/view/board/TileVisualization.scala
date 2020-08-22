package view.board

import model.TileIdentifier.Group
import model.{Tile, TileIdentifier}
import scalafx.beans.property.ReadOnlyDoubleProperty
import scalafx.geometry.Pos._
import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Rectangle, StrokeType}

import scala.util.control.Breaks.break

/**
 * An object which models how a single tile is rendered.
 */
trait TileVisualization extends StackPane {

  def tile: Tile

  def text: String

  def setPiece(piece: PieceVisualization): Unit

  def removePieces(): Unit

  def rectangle: Rectangle

  def applyStyle(graphicDescriptor: GraphicDescriptor): Unit
}

object TileVisualization {

  private class TileVisualizationImpl(val tile: Tile, parentWidth: ReadOnlyDoubleProperty,
                                      parentHeight: ReadOnlyDoubleProperty, rows: Int, cols: Int, val graphicMap: Map[TileIdentifier, GraphicDescriptor]) extends TileVisualization {

    var colorToApply: Color = Color.White // default tile background -> applied if not specified by the user
    var strokeColor: Color = Color.Black // default stroke color -> applied if not specified by the user
    val strokeSize = 3

    var image: Image = _
    var imageContainer: ImageView = _
    var imageFlag: Boolean = false

    if (tile.name.isDefined) {
      graphicMap.get(TileIdentifier(tile.name.get)).foreach(applyStyle)

    } else if (tile.number.isDefined) {
      graphicMap.get(TileIdentifier(tile.number.get)).foreach(applyStyle)

    } else if (tile.groups.nonEmpty) {
      for (group <- tile.groups) {
        if (graphicMap.contains(TileIdentifier(Group(group)))) {
          graphicMap.get(TileIdentifier(Group(group))).foreach(applyStyle)
          break //prendo elemento dal set e controllo che ci sia nella mappa; per ora breakkiamo dopo aver trovato un primo elemento corrispondente
        }
      }
    }

    override def applyStyle(graphicDescriptor: GraphicDescriptor): Unit = {
      if (graphicDescriptor.color.isDefined) {
        colorToApply = graphicDescriptor.color.get
      }

      if (graphicDescriptor.strokeColor.isDefined) {
        strokeColor = graphicDescriptor.strokeColor.get
      }

      if (graphicDescriptor.path.isDefined) {
        var backgroundToApply: String = graphicDescriptor.path.get
        image = new Image(backgroundToApply, parentWidth.getValue / cols, parentHeight.getValue / rows, true, true)
        imageContainer = new ImageView(backgroundToApply)
        imageFlag = true
      }
    }

    var rectangle: Rectangle = new Rectangle {
      fill = colorToApply
      stroke = strokeColor
      strokeType = StrokeType.Inside
      strokeWidth = strokeSize
      width <== parentWidth / cols
      height <== parentHeight / rows
    }

    val text: String = tile.name match {
      case Some(value) => value
      case None => tile.number.get.toString
    }

    var label = new Label(text)

    // to stack things up correctly, add the rectangle itself and the label, then add the image if present
    this.children.addAll(rectangle, label)
    if (imageFlag) {
      this.children.add(imageContainer)
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
  }

  def apply(tile: Tile, parentWidth: ReadOnlyDoubleProperty, parentHeight: ReadOnlyDoubleProperty, rows: Int, cols: Int, map: Map[TileIdentifier, GraphicDescriptor]): TileVisualization =
    new TileVisualizationImpl(tile, parentWidth, parentHeight, rows, cols, map)
}

