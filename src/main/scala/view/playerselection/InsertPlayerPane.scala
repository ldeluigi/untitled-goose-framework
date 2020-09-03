package view.playerselection

import model.entities.runtime.{Piece, Player}
import scalafx.Includes._
import scalafx.beans.binding.Bindings
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, HBox}
import view.ColorUtils

trait InsertPlayerPane extends BorderPane {
  def checkPlayers: Boolean

  def getPlayerSeq: Seq[Player]

  def getPlayersPiecesMap: Map[Player, Piece]
}

object InsertPlayerPane {

  def apply(playersRange: Range): InsertPlayerPane = new InsertPlayerPaneImpl(playersRange)

  class PlayerPiece(n: String, val color: model.Color.Color) {
    val name = new StringProperty(this, "Name", n)
    val colorProp = new ObjectProperty(this, "Piece", ColorUtils.getColor(color))
  }

  private class InsertPlayerPaneImpl(playersRange: Range) extends InsertPlayerPane {

    val playerBuffer: ObservableBuffer[PlayerPiece] = ObservableBuffer()
    val playerPieces: PlayerPieceTable = PlayerPieceTable(playerBuffer)

    val moveUp = new Button("▲")
    val moveDown = new Button("▼")
    val addPlayer: Button = new Button("Add")
    val playerName: Label = new Label("Insert player data:")
    val playerNameFromInput = new TextField
    val colorsChoice = new ComboBox(model.Color.values.toList)
    colorsChoice.getSelectionModel.selectFirst()
    val removePlayer: Button = new Button("Remove")

    val inputPlayers: HBox = new HBox {
      alignment = Pos.Center
      spacing = 15
      padding = Insets(5)
      children = List(playerName, playerNameFromInput, colorsChoice, addPlayer)
    }

    val tableControls: HBox = new HBox {
      alignment = Pos.Center
      spacing = 15
      padding = Insets(5)
      children = List(moveUp, moveDown, removePlayer)
    }

    this.setTop(inputPlayers)
    this.setCenter(playerPieces.tableView)
    this.setBottom(tableControls)


    moveUp.disable <== playerPieces.selectedIndex <= 0
    moveDown.disable <== Bindings.createBooleanBinding(() => {
      val index = playerPieces.selectedIndex.value
      index < 0 || index + 1 >= playerPieces.tableView.getItems.size
    }, playerPieces.selectedIndex, playerPieces.tableView.getItems)

    addPlayer.disable <== playerNameFromInput.text.isEmpty || Bindings.createBooleanBinding(() => {
      playerBuffer.size >= playersRange.`end`
    }, playerBuffer)

    removePlayer.disable <== playerPieces.selectedIndex < 0


    moveUp.onAction = _ => {
      val index = playerPieces.selectedIndex.value
      playerBuffer.add(index - 1, playerBuffer.remove(index))
      playerPieces.tableView.selectionModel.value.clearAndSelect(index - 1)
    }

    moveDown.onAction = _ => {
      val index = playerPieces.selectedIndex.value
      playerBuffer.add(index + 1, playerBuffer.remove(index))
      playerPieces.tableView.selectionModel.value.clearAndSelect(index + 1)
    }

    addPlayer.onAction = _ => {
      if (!playerBuffer.exists(_.name.value == playerNameFromInput.text.get)) {
        playerBuffer += new PlayerPiece(playerNameFromInput.text.value, colorsChoice.value.get)
        playerNameFromInput.clear()
      } else {
        new Alert(AlertType.Error) {
          title = "Error!"
          headerText = "This username is already taken!"
          contentText = "Choose a different name."
        }.showAndWait()
      }
    }

    removePlayer.onAction = _ => {
      val index = playerPieces.tableView.selectionModel.value.getSelectedIndex
      playerBuffer.remove(index)
    }

    override def getPlayersPiecesMap: Map[Player, Piece] =
      playerBuffer.map(p => Player(p.name.value) -> Piece(p.color)).toMap

    override def getPlayerSeq: Seq[Player] =
      playerBuffer.map(p => Player(p.name.value))

    override def checkPlayers: Boolean = playersRange.contains(playerBuffer.size)
  }

}
