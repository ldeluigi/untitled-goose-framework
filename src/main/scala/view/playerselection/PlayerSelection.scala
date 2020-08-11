package view.playerselection

import engine.`match`.Match
import model.entities.board.{Board, Piece}
import model.rules.ruleset.RuleSet
import model.{Color, Player}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ColorPicker, Label, TextField}
import scalafx.scene.layout.{AnchorPane, Background, BackgroundFill, BorderPane, HBox}
import scalafx.scene.paint.Color.{ALICEBLUE, BLUE, Blue, LightGreen, color}
import scalafx.stage.Stage
import view.ApplicationController

class PlayerSelection(stage: Stage, board: Board, ruleSet: RuleSet, widthSize: Int, heightSize: Int) extends Scene {

  val players: Map[Player, Piece] = Map(Player("P1") -> Piece(Color.Red), Player("P2") -> Piece(Color.Blue))
  val borderPane = new BorderPane()
  val anchorPane = new AnchorPane()

  root = borderPane

  val playerName = new Label("Insert player name: ")
  val playerNameFromInput = new TextField()
  val colorsChoice = new ColorPicker()
  val addPlayer = new Button("Add player")
  val startGame = new Button("Start game!")
  val cancel = new Button("Cancel")

  val upperGameNameHeader: HBox = new HBox() {
    alignment = Pos.Center
    padding = Insets(30)
    fill = Blue
    children = new Label("Game Name") // TODO get the actual name of the game
  }

  val centerPlayerConsole: HBox = new HBox() {
    alignment = Pos.Center
    spacing = 30
    padding = Insets(30)
    children = List(playerName, playerNameFromInput, colorsChoice, addPlayer)
  }

  val bottomGameControls: HBox = new HBox() {
    alignment = Pos.BottomRight
    spacing = 30
    padding = Insets(30)
    children = List(startGame, cancel)
  }

  borderPane.top = upperGameNameHeader
  borderPane.center = centerPlayerConsole
  borderPane.bottom = bottomGameControls

  addPlayer.onAction = _ => {
    playerNameFromInput.clear()
    // add entry to players map
  }

  startGame.onAction = _ => {
    val currentMatch: Match = Match(board, players, ruleSet)
    val appView: ApplicationController = ApplicationController(stage, widthSize, heightSize, currentMatch)
  }

  cancel.onAction = _ => {
    playerNameFromInput.clear()
  }

}
