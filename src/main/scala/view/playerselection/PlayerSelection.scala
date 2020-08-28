package view.playerselection

import model.entities.board.Piece
import model.game.Game
import model.{GameData, Player, TileIdentifier}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, HBox, VBox}
import scalafx.scene.paint.Color.DarkGreen
import scalafx.scene.text.Text
import scalafx.stage.Stage
import view.ApplicationController
import view.board.GraphicDescriptor

import scala.collection.mutable.ListBuffer

/** A scene used to be able to add new players to the game.
 *
 * @param stage      the stage on which to render the selection
 * @param gameData   a container of the board definition and ruleSet of the current game
 * @param widthSize  width of the scene
 * @param heightSize height of the scene
 * @param graphicMap the graphic properties container
 */
class PlayerSelection(stage: Stage, gameData: GameData, widthSize: Int, heightSize: Int, graphicMap: Map[TileIdentifier, GraphicDescriptor]) extends Scene {

  var enrolledPlayers: Map[Player, Piece] = Map()
  val borderPane = new BorderPane

  root = borderPane

  val playerNameFromInput = new TextField

  val graphicList = new ListBuffer[String]

  val colorsChoice = new ComboBox(model.Color.values.toList)
  colorsChoice.getSelectionModel.selectFirst()
  //val minimumPlayers: Int = Some
  //val maximumPlayers: Int = Some

  val addPlayer: Button = new Button {
    text = "Add"
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
    text = "Insert player name:"
    style = "-fx-font-size: 12pt"
  }

  val centerPlayerConsole: HBox = new HBox {
    alignment = Pos.Center
    spacing = 15
    padding = Insets(30)
    children = List(playerName, playerNameFromInput, colorsChoice, addPlayer, removePlayer)
  }


  val activePlayersList: TextArea = new TextArea
  activePlayersList.setMaxSize(widthSize * 0.15, heightSize)

  val activePlayersPanel: VBox = new VBox {
    spacing = 15
    padding = Insets(15)
    children = List(new Label("Currently enrolled players:"), activePlayersList)
  }

  val bottomGameControls: HBox = new HBox {
    alignment = Pos.BottomCenter
    spacing = 15
    padding = Insets(15)
    children = List(startGame)
  }

  addPlayer.onAction = _ => {
    if (playerNameFromInput.getText.nonEmpty) {
      if (!enrolledPlayers.contains(Player(playerNameFromInput.getText))) {
        enrolledPlayers += (Player(playerNameFromInput.getText) -> Piece(colorsChoice.getValue))
        renderGraphicalAddition()
        playerNameFromInput.clear()
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
    if (playerNameFromInput.getText.nonEmpty) {
      enrolledPlayers -= Player(playerNameFromInput.getText)
      renderGraphicalRemoval()
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

  startGame.onAction = _ => {
    if (enrolledPlayers.nonEmpty) {
      val currentMatch: Game = Game(gameData.board, enrolledPlayers, gameData.ruleSet)
      val appView: ApplicationController = ApplicationController(stage, widthSize, heightSize, currentMatch, graphicMap)
      stage.scene = appView
    } else {
      new Alert(AlertType.Error) {
        initOwner(stage)
        title = "Error!"
        headerText = "Missing players"
        contentText = "Can't start a game without players"
      }.showAndWait()
    }
  }

  /** Utility method to add new user specified players to the panel containing the current list of players. */
  def renderGraphicalAddition(): Unit = {
    activePlayersList.appendText("Name: " + playerNameFromInput.getText + "\t" + "color: " + colorsChoice.getValue + "\n")
  }

  /** Utility method to remove a user specified players to the panel containing the current list of players. */
  def renderGraphicalRemoval(): Unit = {
    activePlayersList.clear()
    enrolledPlayers.foreach(entry => activePlayersList.appendText("Name: " + entry._1.name + "\t" + "color: " + entry._2.color + "\n"))
  }

  borderPane.top = upperGameNameHeader
  borderPane.center = centerPlayerConsole
  borderPane.bottom = bottomGameControls
  borderPane.right = activePlayersPanel
}
