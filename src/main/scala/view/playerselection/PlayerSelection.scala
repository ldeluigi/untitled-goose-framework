package view.playerselection

import model.entities.board.Piece
import model.game.Game
import model.{GameData, Player, TileIdentifier}
import scalafx.Includes._
import scalafx.beans.binding.Bindings
import scalafx.beans.property.{ObjectProperty, ReadOnlyIntegerProperty, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.TableColumn._
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.DarkGreen
import scalafx.scene.shape.Circle
import scalafx.scene.text.Text
import scalafx.stage.Stage
import view.board.GraphicDescriptor
import view.{ApplicationController, ColorUtils}

class PlayerPiece(n: String, val color: model.Color.Color) {
  val name = new StringProperty(this, "Name", n)
  val colorProp = new ObjectProperty(this, "Piece", ColorUtils.getColor(color))
}

/** A scene used to be able to add new players to the game.
 *
 * @param stage      the stage on which to render the selection
 * @param gameData   a container of the board definition and ruleSet of the current game
 * @param widthSize  width of the scene
 * @param heightSize height of the scene
 * @param graphicMap the graphic properties container
 */
class PlayerSelection(stage: Stage, gameData: GameData, widthSize: Int, heightSize: Int, graphicMap: Map[TileIdentifier, GraphicDescriptor]) extends Scene {


  val borderPane = new BorderPane

  root = borderPane

  var playerPieces: ObservableBuffer[PlayerPiece] = ObservableBuffer()

  val nameCol: TableColumn[PlayerPiece, String] = new TableColumn[PlayerPiece, String] {
    text = "Name"
    cellValueFactory = {
      _.value.name
    }
  }

  val colorCol: TableColumn[PlayerPiece, Color] = new TableColumn[PlayerPiece, Color] {
    text = "Piece"
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


  val activePlayersTable: TableView[PlayerPiece] = new TableView(playerPieces) {
    columns ++= List(nameCol, colorCol)
  }
  activePlayersTable.setMaxSize(widthSize * 0.15, heightSize)
  activePlayersTable.editable = false
  colorCol.sortable = false
  nameCol.sortable = false

  val moveUp = new Button("▲")
  val moveDown = new Button("▼")

  val selectedIndex: ReadOnlyIntegerProperty = activePlayersTable.selectionModel.value.selectedIndexProperty()

  moveUp.disable <== selectedIndex <= 0
  moveDown.disable <== Bindings.createBooleanBinding(() => {
    val index = selectedIndex.value
    index < 0 || index + 1 >= activePlayersTable.getItems.size
  }, selectedIndex, activePlayersTable.getItems)


  moveUp.onAction = _ => {
    val index = activePlayersTable.selectionModel.value.getSelectedIndex
    playerPieces.add(index - 1, playerPieces.remove(index))
    activePlayersTable.selectionModel.value.clearAndSelect(index - 1)
  }

  moveDown.onAction = _ => {
    val index = activePlayersTable.selectionModel.value.getSelectedIndex
    playerPieces.add(index + 1, playerPieces.remove(index))
    activePlayersTable.selectionModel.value.clearAndSelect(index + 1)
  }

  val playerNameFromInput = new TextField

  val colorsChoice = new ComboBox(model.Color.values.toList)
  colorsChoice.getSelectionModel.selectFirst()

  val addPlayer: Button = new Button {
    text = "Enroll"
    style = "-fx-font-size: 12pt"
  }

  val removePlayer: Button = new Button {
    text = "Remove"
    style = "-fx-font-size: 12pt"
  }

  val startGame: Button = new Button {
    text = "Start game!"
    textFill = DarkGreen
    style = "-fx-font-size: 15pt"
  }

  val upperGameNameHeader: HBox = new HBox {
    alignment = Pos.Center
    padding = Insets(30)
    children = new Text {
      text = "Untitled Goose Framework"
      style = "-fx-font-size: 28pt"
    }
  }

  val playerName: Text = new Text {
    text = "Insert player data:"
    style = "-fx-font-size: 12pt"
  }

  val playersLabel: Text = new Text {
    text = "Currently enrolled players:"
    style = "-fx-font-size: 12pt"
  }

  val inputPlayers: HBox = new HBox {
    alignment = Pos.Center
    spacing = 15
    padding = Insets(5)
    children = List(playerName, playerNameFromInput, colorsChoice)
  }

  val playerControls: HBox = new HBox {
    alignment = Pos.TopCenter
    spacing = 15
    padding = Insets(5)
    children = List(addPlayer, removePlayer)
  }

  val centerConsole: VBox = new VBox {
    alignment = Pos.Center
    spacing = 30
    padding = Insets(15)
    children = List(inputPlayers, playerControls)
  }

  val activePlayersPanel: VBox = new VBox {
    spacing = 30
    padding = Insets(30)
    children = List(playersLabel, activePlayersTable, moveUp, moveDown)
  }

  val bottomGameControls: HBox = new HBox {
    alignment = Pos.BottomCenter
    spacing = 15
    padding = Insets(15)
    children = List(startGame)
  }

  addPlayer.onAction = _ => {
    if (playerNameFromInput.text.value.nonEmpty) {
      if (!playerPieces.exists(_.name.value == playerNameFromInput.text.get)) {
        if (playerPieces.size >= gameData.playersRange.`end`) {
          new Alert(AlertType.Error) {
            initOwner(stage)
            title = "Error!"
            headerText = "Maximum number of players reached!"
            contentText = "Remove one and try again."
          }.showAndWait()
        } else {
          playerPieces += new PlayerPiece(playerNameFromInput.text.value, colorsChoice.value.get)
          playerNameFromInput.clear()
        }
      } else {
        new Alert(AlertType.Error) {
          initOwner(stage)
          title = "Error!"
          headerText = "This username is already taken!"
          contentText = "Choose a different name."
        }.showAndWait()
      }
    } else {
      new Alert(AlertType.Error) {
        initOwner(stage)
        title = "Error!"
        headerText = "Missing parameters!"
        contentText = "Specify player's name and color."
      }.showAndWait()
    }
  }

  removePlayer.disable <== selectedIndex < 0
  removePlayer.onAction = _ => {
    val index = activePlayersTable.selectionModel.value.getSelectedIndex
    playerPieces.remove(index)
  }

  def getPlayersPiecesMap: Map[Player, Piece] =
    playerPieces.map(p => Player(p.name.value) -> Piece(p.color)).toMap

  def getPlayerSeq: Seq[Player] =
    playerPieces.map(p => Player(p.name.value))

  startGame.onAction = _ => {
    getPlayerSeq.foreach(println)
    val minimumNeededPlayers: Int = gameData.playersRange.start
    if (playerPieces.nonEmpty) {
      if (playerPieces.size >= minimumNeededPlayers) {
        val currentMatch: Game = gameData.createGame(getPlayerSeq, getPlayersPiecesMap)
        val appView: ApplicationController = ApplicationController(stage, widthSize, heightSize, currentMatch, graphicMap)
        stage.scene = appView
      } else {
        new Alert(AlertType.Error) {
          initOwner(stage)
          title = "Error!"
          headerText = "You need at least " + minimumNeededPlayers + " players to start this game!"
          contentText = "Add " + (minimumNeededPlayers - playerPieces.size) + " more players."
        }.showAndWait()
      }
    } else {
      new Alert(AlertType.Error) {
        initOwner(stage)
        title = "Error!"
        headerText = "Missing players!"
        contentText = "Can't start a game without players"
      }.showAndWait()
    }
  }

  borderPane.top = upperGameNameHeader
  borderPane.center = centerConsole
  borderPane.bottom = bottomGameControls
  borderPane.right = activePlayersPanel
}
