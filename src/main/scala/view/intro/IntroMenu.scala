package view.intro

import engine.`match`.Match
import model.{Color, Player}
import model.entities.board.{Board, Piece}
import model.rules.ruleset.RuleSet
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ComboBox, Label, TextField}
import scalafx.scene.layout.BorderPane
import view.ApplicationController

class IntroMenu(board: Board, ruleSet: RuleSet, widthSize: Int, heightSize: Int) extends Scene {

  val borderPane = new BorderPane()
  this.content = borderPane

  val playerName = new Label("Insert player name: ")
  val playerNameFromInput = new TextField()
  val colorsChoice = new ComboBox() // TODO add colors
  val addPlayer = new Button("Add player to the game")
  val startGame = new Button("Start game!")

  //Generate an entry of this map whenever pressing addPlayer Button
  val players: Map[Player, Piece] = Map(Player("P1") -> Piece(Color.Red), Player("P2") -> Piece(Color.Blue))


  //create this when pressing StartGame
  val currentMatch: Match = Match(board, players, ruleSet)
  val appView: ApplicationController = ApplicationController(widthSize, heightSize, currentMatch)
  //then set appView as new scene on the current stage

}
