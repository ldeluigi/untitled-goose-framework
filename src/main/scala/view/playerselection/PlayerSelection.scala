package view.playerselection

import model.Player
import model.entities.board.{Board, Piece}
import model.game.Game
import model.rules.ruleset.RuleSet
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.{AnchorPane, BorderPane, HBox, VBox}
import scalafx.scene.paint.Color.DarkGreen
import scalafx.scene.text.Text
import scalafx.stage.Stage
import view.ApplicationController

class PlayerSelection(stage: Stage, board: Board, ruleSet: RuleSet, widthSize: Int, heightSize: Int) extends Scene {

  var enrolledPlayers: Map[Player, Piece] = Map()
  val borderPane = new BorderPane
  val anchorPane = new AnchorPane

  root = borderPane

  val playerNameFromInput = new TextField()

  /*val cmb = new ComboBox()
  cmb.getItems.addAll()

  cmb.setCellFactory(new Callback[ListView[Color], ListCell[Color]]() {
     override def call(p: ListView[Color]): ListCell[Color] = new ListCell() {
       private val rectangle = Rectangle(5, 5)

       override def updateItem(item: Color, empty: Boolean): Unit = {
        super.updateItem(item, empty)
        if (item == null || empty) graphic
        else {
          rectangle.fill
          graphic = (rectangle)
        }
      }
    }
  })*/

  val colorsChoice = new ComboBox(List(model.Color.Red, model.Color.Blue, model.Color.Yellow, model.Color.Orange, model.Color.Green, model.Color.Purple))

  val addPlayer: Button = new Button {
    text = "Enroll player"
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
    spacing = 30
    padding = Insets(30)
    children = List(playerName, playerNameFromInput, colorsChoice, addPlayer)
  }

  val activePlayersList: TextArea = new TextArea {
    text = "Currently enrolled players:" + "\n"
  }
  activePlayersList.setMaxSize(widthSize * 0.15, heightSize)


  val activePlayersPanel: VBox = new VBox {
    spacing = 15
    padding = Insets(20)
    children = List(activePlayersList)
  }

  val bottomGameControls: HBox = new HBox {
    alignment = Pos.BottomCenter
    spacing = 15
    padding = Insets(20)
    children = List(startGame)
  }

  addPlayer.onAction = _ => {
    enrolledPlayers += (Player(playerNameFromInput.getText) -> Piece(colorsChoice.getValue))
    refreshPlayersList()
    playerNameFromInput.clear()
  }

  startGame.onAction = _ => {
    if (enrolledPlayers.nonEmpty) {
      val currentMatch: Game = Game(board, enrolledPlayers, ruleSet)
      val appView: ApplicationController = ApplicationController(stage, widthSize, heightSize, currentMatch)
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

  def refreshPlayersList(): Unit = {
    activePlayersList.appendText("Name: " + playerNameFromInput.getText + "\t" + "color: " + colorsChoice.getValue + "\n")
  }

  borderPane.top = upperGameNameHeader
  borderPane.center = centerPlayerConsole
  borderPane.bottom = bottomGameControls
  borderPane.right = activePlayersPanel

}
