package untitled.goose.framework.view.scalafx.playerselection

import scalafx.Includes._
import scalafx.beans.binding.Bindings
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, HBox}
import scalafx.stage.Stage
import untitled.goose.framework.model
import untitled.goose.framework.model.Colour
import untitled.goose.framework.model.entities.runtime.{Piece, Player}
import untitled.goose.framework.view.scalafx.ColorUtils

/**
 * A custom pane to insert the player to play the game and customize their playing order in the game.
 */
trait InsertPlayerPane extends BorderPane {

  /** Checks if the players can fit into the game's players min/max range.
   *
   * @return the boolean property regarding the fitting range.
   */
  def checkPlayers: Boolean

  /** Gets a sequence of the current players names.
   *
   * @return the sequence that holds all the current players names.
   */
  def getPlayerSeq: Seq[Player]

  /** Maps a player to its piece.
   *
   * @return the map containing the player and related piece.
   */
  def getPlayersPiecesMap: Map[Player, Piece]
}

object InsertPlayerPane {

  /**
   * Factory Method for a InsertPlayerPane.
   *
   * @param playersRange the minimum and maximum number of players allowed.
   * @param stage        the stage containing this pane.
   * @return a new InsertPlayerPane.
   */
  def apply(playersRange: Range, stage: Stage): InsertPlayerPane = new InsertPlayerPaneImpl(playersRange, stage)

  private[scalafx] class PlayerPiece(n: String, val color: Colour) {
    val name = new StringProperty(this, "Name", n)
    val colorProp = new ObjectProperty(this, "Piece", ColorUtils.getColor(color))
  }

  private class InsertPlayerPaneImpl(playersRange: Range, stage: Stage) extends InsertPlayerPane {

    private val playerBuffer: ObservableBuffer[PlayerPiece] = ObservableBuffer()
    private val playerPieces: PlayerPieceTable = PlayerPieceTable(playerBuffer)

    private val moveUp = new Button("▲")
    private val moveDown = new Button("▼")
    private val addPlayer: Button = new Button("Add")
    private val playerName: Label = new Label("Insert player data:")
    private val playerNameFromInput = new TextField
    private val colorsChoice = new ComboBox(model.Colour.Default.colours)
    colorsChoice.getSelectionModel.selectFirst()
    private val removePlayer: Button = new Button("Remove")

    private val inputPlayers: HBox = new HBox {
      alignment = Pos.Center
      spacing = 15
      children = List(playerName, playerNameFromInput, colorsChoice, addPlayer)
    }
    inputPlayers.styleClass.add("inputPlayers")

    private val tableControls: HBox = new HBox {
      alignment = Pos.Center
      spacing = 15
      children = List(moveUp, moveDown, removePlayer)
    }
    tableControls.styleClass.add("tableControls")

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
          initOwner(stage)
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
