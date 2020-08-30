package view.playerselection

import model.entities.board.Piece
import model.game.Game
import model.{GameData, Player, TileIdentifier}
import scalafx.beans.property.{ObjectProperty, StringProperty}
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

import scala.collection.mutable.ListBuffer

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

  val activePlayersTable: TableView[PlayerPiece] = new TableView(playerPieces) {
    columns ++= List(
      new TableColumn[PlayerPiece, String] {
        text = "Name"
        cellValueFactory = {
          _.value.name
        }
      },
      new TableColumn[PlayerPiece, Color] {
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
    )
  }
  activePlayersTable.setMaxSize(widthSize * 0.15, heightSize)


  val playerNameFromInput = new TextField

  val graphicList = new ListBuffer[String]

  var playerOrderList: Seq[Player] = Seq()

  val colorsChoice = new ComboBox(model.Color.values.toList)
  colorsChoice.getSelectionModel.selectFirst()

  val addPlayer: Button = new Button {
    text = "Enroll"
    style = "-fx-font-size: 12pt"
  }

  val removePlayer: Button = new Button {
    text = "Withdraw"
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
    children = List(playersLabel, activePlayersTable)
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
          val player = Player(playerNameFromInput.text.value)
          playerOrderList :+= player
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

  removePlayer.onAction = _ => {
    if (playerNameFromInput.text.value.nonEmpty) {
      playerOrderList = playerOrderList.filterNot(_.name == playerNameFromInput.text.value)
      playerPieces.removeIf(_.name.value == playerNameFromInput.text.value)
      playerNameFromInput.clear()
    } else {
      new Alert(AlertType.Error) {
        initOwner(stage)
        title = "Error!"
        headerText = "Missing player to remove!"
        contentText = "Specify player's name to remove him/her from the game."
      }.showAndWait()
    }
  }

  def createPlayerMap(): Map[Player, Piece] =
    playerPieces.map(p => Player(p.name.value) -> Piece(p.color)).toMap

  startGame.onAction = _ => {
    val minimumNeededPlayers: Int = gameData.playersRange.start
    if (playerPieces.nonEmpty) {
      if (playerPieces.size >= minimumNeededPlayers) {
        val currentMatch: Game = gameData.createGame(playerOrderList, createPlayerMap())
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
