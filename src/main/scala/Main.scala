import view.ApplicationController
import scalafx.application.JFXApp
import java.awt.Dimension
import java.awt.Toolkit

import engine.`match`.Match
import javafx.scene.input.KeyCode
import model.entities.board.Board


object Main extends JFXApp {

  val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize
  val players = ???
  val ruleSet = ???
  val currentMatch = Match(Board(), players, ruleSet)
  val appView = ApplicationController(screenSize.width, screenSize.height, currentMatch)

  stage = new JFXApp.PrimaryStage {
    title.value = "Untitled Goose Framework"
    width = 0.5 * screenSize.width
    height = 0.5 * screenSize.height
    resizable = true
    //fullScreen = true
    minWidth = 0.5 * screenSize.width
    minHeight = 0.5 * screenSize.height
    scene = appView
    fullScreenExitHint = "Premi esc per uscire"
  }
  stage.getScene.setOnKeyPressed(
    key => if (key.getCode == KeyCode.F11) {
      stage.setFullScreen(true)
    }
  )
}