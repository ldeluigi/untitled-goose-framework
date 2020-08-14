package view.playerselection

import model.`match`.Match
import model.entities.board.{Board, Piece}
import model.rules.ruleset.RuleSet
import model.{Color, Player}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ColorPicker, TextField}
import scalafx.scene.layout.{AnchorPane, BorderPane, HBox}
import scalafx.scene.paint.Color.{Blue, DarkGreen, DarkOliveGreen, DarkRed, IndianRed, MediumVioletRed, Red}
import scalafx.scene.text.Text
import scalafx.stage.Stage
import view.ApplicationController

class PlayerSelection(stage: Stage, board: Board, ruleSet: RuleSet, widthSize: Int, heightSize: Int) extends Scene {

  val players: Map[Player, Piece] = Map(Player("P1") -> Piece(Color.Red), Player("P2") -> Piece(Color.Blue))
  val borderPane = new BorderPane()
  val anchorPane = new AnchorPane()

  root = borderPane

  val playerNameFromInput = new TextField()
  val colorsChoice = new ColorPicker()

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
    playerNameFromInput.clear()
    // TODO add entry to players map
  }

  startGame.onAction = _ => {
    val currentMatch: Match = Match(board, players, ruleSet)
    val appView: ApplicationController = ApplicationController(stage, widthSize, heightSize, currentMatch)
  }

  cancel.onAction = _ => {
    playerNameFromInput.clear()
  }

}
