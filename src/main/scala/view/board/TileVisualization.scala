package view.board

import model.TileIdentifier.Group
import model.{Tile, TileIdentifier}
import scalafx.beans.property.ReadOnlyDoubleProperty
import scalafx.geometry.Pos._
import scalafx.scene.control.Label
import scalafx.scene.image.Image
import scalafx.scene.layout.StackPane
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._
import scalafx.scene.shape.{Rectangle, StrokeType}

import scala.util.control.Breaks.break

trait TileVisualization extends StackPane {

  def tile: Tile

  def text: String

  def setPiece(piece: PieceVisualization): Unit

  def removePieces(): Unit

  def rectangle: Rectangle

  def applyStyle(graphicDescriptor: GraphicDescriptor): Unit
}

/* Per una data tile se nella mappa c'è una data entry allora diventa quello
* passo una mappa con tileidentifier e graphdescriptor
* se la val tile ha un nome vado nella mappa e faccio contains o try get con optional se c'è o non c'è (uso metodi della mappa).
* se c'è una entry che corrisponde ad un tileidentifier di quel nome. <- per fare la contains devo dargli un nuovo tileIdentifier
* creato con la stringa che prendo dalla tile.
*
* ho la mappa, ho la tile, guardo la tile se questa ha il nome allora provo a vedere se la mappa aha un valore relativo al tile identifirer(nome)
* se c'è vado a vedere cosa c'è scritto e lo applico alla tile
* altrimenti passo ai numeri oppure ai gruppi.
* Al primo che trovo mi fermo!
* */

object TileVisualization {

  private class TileVisualizationImpl(val tile: Tile, parentWidth: ReadOnlyDoubleProperty,
                                      parentHeight: ReadOnlyDoubleProperty, rows: Int, cols: Int, val graphicMap: Map[TileIdentifier, GraphicDescriptor]) extends TileVisualization {

    var colorToApply: Color = Color.White // default tile background
    var backgroundToApply: Image = ???

    if (tile.name.isDefined) {
      /*se definito applicarlo alla tile visualization -> ad esempio se ho il colore allora lo applico MA ricordarsi la lista di priorità*/
      graphicMap.get(TileIdentifier(tile.name.get)).foreach(applyStyle)

    } else if (tile.number.isDefined) {
      graphicMap.get(TileIdentifier(tile.number.get)).foreach(applyStyle)

    } else if (tile.groups.nonEmpty) {
      //prendo elemento dal set e controllo che ci sia nella mappa; per ora breakkiamo dopo aver trovato un primo elemento corrispondente
      for (group <- tile.groups) {
        if (graphicMap.contains(TileIdentifier(Group(group)))) {
          graphicMap.get(TileIdentifier(Group(group))).foreach(applyStyle)
          break
        }
      }
    }

    override def applyStyle(graphicDescriptor: GraphicDescriptor): Unit = {
      if(graphicDescriptor.color.isDefined){
        colorToApply = graphicDescriptor.color.get
      }
      if(graphicDescriptor.path.isDefined){
        /**/
      }
    }

    val strokeSize = 3

    var rectangle: Rectangle = new Rectangle {
      fill = colorToApply
      stroke = Black
      strokeType = StrokeType.Inside
      strokeWidth = strokeSize
      width <== (parentWidth / cols)
      height <== parentHeight / rows
    }

    val text: String = tile.name match {
      case Some(value) => value
      case None => tile.number.get.toString
    }

    var label = new Label(text)

    this.children.addAll(rectangle, label)

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

