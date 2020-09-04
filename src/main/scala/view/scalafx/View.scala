package view.scalafx

import java.awt.{Dimension, Toolkit}

import javafx.scene.input.KeyCode
import model.TileIdentifier
import model.entities.runtime.GameTemplate
import scalafx.application.JFXApp
import scalafx.scene.image.Image
import view.scalafx.board.GraphicDescriptor
import view.scalafx.playerselection.IntroMenu

class View(gameData: GameTemplate, graphicMap: Map[TileIdentifier, GraphicDescriptor]) extends JFXApp {
  val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize
  val appTitle = "Untitled Goose Framework"

  stage = new JFXApp.PrimaryStage {
    title.value = appTitle
    width = 0.5 * screenSize.width
    height = 0.5 * screenSize.height
    resizable = true
    //fullScreen = true
    minWidth = 0.5 * screenSize.width
    minHeight = 0.5 * screenSize.height
    scene = new IntroMenu(this, gameData, appTitle, screenSize.width, screenSize.height, graphicMap)
    fullScreenExitHint = "Press esc to leave full screen mode"
  }

  stage.getIcons.add(new Image("GooseLogo.png"))

  stage.getScene.setOnKeyPressed(
    key => if (key.getCode == KeyCode.F11) {
      stage.setFullScreen(true)
    }
  )

}
