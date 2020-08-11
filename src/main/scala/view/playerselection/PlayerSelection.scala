package view.playerselection

import engine.`match`.Match
import model.entities.board.{Board, Piece}
import model.rules.ruleset.RuleSet
import model.{Color, Player}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ComboBox, Label, TextField}
import scalafx.scene.layout.{BorderPane, HBox}
import scalafx.stage.Stage
import view.ApplicationController

class PlayerSelection(stage: Stage, board: Board, ruleSet: RuleSet, widthSize: Int, heightSize: Int) extends Scene {

  val borderPane = new BorderPane()
  root = borderPane

  val playerName = new Label("Insert player name: ")
  val playerNameFromInput = new TextField()
  val colorsChoice = new ComboBox() // TODO add colors
  val addPlayer = new Button("Add player to the game")
  val startGame = new Button("Start game!")

  val innerSelection = new HBox(playerName, playerNameFromInput, colorsChoice, addPlayer)
  innerSelection.spacing = 5

  borderPane.top = new Label ("Game name")
  borderPane.center = innerSelection
  borderPane.bottom = startGame



  //Generates an entry of this map whenever pressing addPlayer Button
  val players: Map[Player, Piece] = Map(Player("P1") -> Piece(Color.Red), Player("P2") -> Piece(Color.Blue))

  addPlayer.onAction = (_) => {

  }

  startGame.onAction = (_) => {
    val currentMatch: Match = Match(board, players, ruleSet)
    val appView: ApplicationController = ApplicationController(widthSize, heightSize, currentMatch)
  }

}
