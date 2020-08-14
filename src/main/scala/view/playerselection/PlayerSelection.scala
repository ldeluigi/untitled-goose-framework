package view.playerselection

import model.entities.board.{Board, Piece}
import model.game.Game
import model.rules.ruleset.RuleSet
import model.{Color, Player}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ComboBox, TextField}
import scalafx.scene.layout.{AnchorPane, BorderPane, HBox}
import scalafx.scene.paint.Color.{DarkGreen, IndianRed}
import scalafx.scene.text.Text
import scalafx.stage.Stage
import view.ApplicationController

class PlayerSelection(stage: Stage, board: Board, ruleSet: RuleSet, widthSize: Int, heightSize: Int) extends Scene {

  var players: Map[Player, Piece] = Map(Player("P1") -> Piece(Color.Red), Player("P2") -> Piece(Color.Blue))
  val borderPane = new BorderPane()
  val anchorPane = new AnchorPane()

  root = borderPane

  val playerNameFromInput = new TextField()
  // TODO convert this into a predetermined List from model.Colors enum
  val colorsChoice = new ComboBox(List(model.Color.Red, model.Color.Blue, model.Color.Yellow, model.Color.Orange, model.Color.Green, model.Color.Purple))

  val addPlayer: Button = new Button() {
    text = "Add player to game"
    style = "-fx-font-size: 12pt"
  }

  val startGame: Button = new Button() {
    text = "Start game!"
    textFill = DarkGreen
    style = "-fx-font-size: 15pt"
  }
  val cancel: Button = new Button() {
    text = "Cancel"
    style = "-fx-font-size: 11pt"
    textFill = IndianRed
  }

  val upperGameNameHeader: HBox = new HBox() {
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

  val centerPlayerConsole: HBox = new HBox() {
    alignment = Pos.Center
    spacing = 30
    padding = Insets(30)
    children = List(playerName, playerNameFromInput, colorsChoice, addPlayer)
  }

  val bottomGameControls: HBox = new HBox() {
    alignment = Pos.BottomRight
    spacing = 15
    padding = Insets(20)
    children = List(cancel, startGame)
  }

  borderPane.top = upperGameNameHeader
  borderPane.center = centerPlayerConsole
  borderPane.bottom = bottomGameControls

  addPlayer.onAction = _ => {
    players += (Player(playerNameFromInput.getText) -> Piece(colorsChoice.getValue))
    playerNameFromInput.clear()
  }

  startGame.onAction = _ => {
    val currentMatch: Game = Game(board, players, ruleSet)
    val appView: ApplicationController = ApplicationController(stage, widthSize, heightSize, currentMatch)
  }

  cancel.onAction = _ => {
    playerNameFromInput.clear()
  }

}
