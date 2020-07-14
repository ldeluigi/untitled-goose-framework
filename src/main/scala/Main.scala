import view.{ApplicationView, BoardStage, BoardView}
import scalafx.application.JFXApp
import java.awt.Dimension
import java.awt.Toolkit

import javafx.scene.input.KeyCode
import model.entities.board.Board


object Main extends JFXApp {

  //val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize
  val screenSize: Dimension = new Dimension(720, 400)
  val appView = ApplicationView(screenSize.width, screenSize.height)
  appView.setBoard(Board()) //TODO change
  appView.setMatchState(null) //TODO change

  stage = new JFXApp.PrimaryStage {
    title.value = "Untitled Goose Framework"
    width = screenSize.width
    height = screenSize.height
    resizable = true
    //fullScreen = true
    scene = appView
    fullScreenExitHint = "Premi esc per uscire"
  }
  stage.getScene.setOnKeyPressed(
    key => if (key.getCode == KeyCode.F11) {
      stage.setFullScreen(true)
    }
  )
}