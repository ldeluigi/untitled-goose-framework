import view.BoardStage
import scalafx.application.JFXApp
import java.awt.Dimension
import java.awt.Toolkit

import javafx.scene.input.KeyCode


object Main extends JFXApp {

  val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize
  stage = new JFXApp.PrimaryStage {
    title.value = "Untitled Goose Framework"
    width = screenSize.width
    height = screenSize.height
    resizable = false
    fullScreen = true
    scene = BoardStage.apply(3)
    fullScreenExitHint = "Premi esc per uscire"
  }
  stage.getScene.setOnKeyPressed(
    key => if(key.getCode == KeyCode.F11) {
      stage.setFullScreen(true)
    }
  )
}