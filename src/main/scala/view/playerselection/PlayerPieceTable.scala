package view.playerselection

import scalafx.Includes._
import scalafx.beans.property.ReadOnlyIntegerProperty
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{TableCell, TableColumn, TableView}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import view.playerselection.InsertPlayerPane.PlayerPiece

case class PlayerPieceTable(playerPieces: ObservableBuffer[PlayerPiece]) {

  private val customWidth: Double = 600

  private val orderCol: TableColumn[PlayerPiece, Int] = new TableColumn[PlayerPiece, Int] {
    text = "Order"
    cellFactory = c => {
      val cell = new TableCell[PlayerPiece, Int]()
      cell.text <== when(!cell.empty) choose (cell.index + 1).asString() otherwise ""
      cell
    }
    prefWidth = customWidth / 3 - 1
  }

  private val nameCol: TableColumn[PlayerPiece, String] = new TableColumn[PlayerPiece, String] {
    text = "Name"
    cellValueFactory = {
      _.value.name
    }
    prefWidth = customWidth / 3 - 1
  }
  private val colorCol: TableColumn[PlayerPiece, Color] = new TableColumn[PlayerPiece, Color] {
    text = "Piece"
    prefWidth = customWidth / 3
    cellValueFactory = {
      _.value.colorProp
    }
    cellFactory = { _ =>
      new TableCell[PlayerPiece, Color] {
        item.onChange { (_, _, newColor) =>
          graphic = if (newColor != null)
            new Circle {
              fill = newColor
              radius = 8
            } else null
        }
      }
    }
  }
  val tableView: TableView[PlayerPiece] = new TableView(playerPieces) {
    columns ++= List(orderCol, nameCol, colorCol)
  }
  tableView.editable = false
  colorCol.sortable = false
  nameCol.sortable = false
  tableView.maxWidth = customWidth

  val selectedIndex: ReadOnlyIntegerProperty = tableView.selectionModel.value.selectedIndexProperty()

}
