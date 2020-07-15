import view.ApplicationController
import scalafx.application.JFXApp
import java.awt.Dimension
import java.awt.Toolkit

import engine.`match`.Match
import javafx.scene.input.KeyCode
import model.Player
import model.actions.{Action, StepForwardAction}
import model.entities.board.{Board, Piece}
import model.rules.RuleSet


object Main extends JFXApp {

  val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize
  val board: Board = Board()
  val players: Map[Player, Piece] = Map(Player("P1") -> Piece())
  val actions: Set[Action] = Set(new StepForwardAction())
  val ruleSet: RuleSet = RuleSet(actions)

  val currentMatch: Match = Match(board, players, ruleSet)
  val appView: ApplicationController = ApplicationController(screenSize.width, screenSize.height, currentMatch)

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