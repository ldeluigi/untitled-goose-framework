package view.intro

import java.awt.{Dimension, Toolkit}

import model.entities.board.Board
import model.rules.ruleset.RuleSet
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ComboBox, Label, TextField}
import scalafx.scene.layout.BorderPane

class IntroMenu(board: Board, ruleset: RuleSet) extends JFXApp {
  val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize

  stage = new JFXApp.PrimaryStage {
    title.value = board.name
    width = 0.5 * screenSize.width
    height = 0.5 * screenSize.height
    resizable = false
    scene = new Scene {
      root = new BorderPane {
        val playerName = new Label(("Insert player name: "))
        val playerNameFromInput = new TextField()
        val colorsChoice = new ComboBox() // TODO add colors
        val addPlayer = new Button("Add player to the game")
        val startGame = new Button("Start game!")
      }
    }
  }
}
