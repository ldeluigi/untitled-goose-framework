package view

import java.awt.{Dimension, Toolkit}

import javafx.scene.input.KeyCode
import model.{GameData, TileIdentifier}
import scalafx.application.JFXApp
import view.board.GraphicDescriptor
import view.playerselection.PlayerSelection

class View(gameData: GameData, graphicMap: Map[TileIdentifier, GraphicDescriptor]) extends JFXApp {
  val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize
  stage = new JFXApp.PrimaryStage {
    title.value = "Untitled Goose Framework"
    width = 0.5 * screenSize.width
    height = 0.5 * screenSize.height
    resizable = true
    //fullScreen = true
    minWidth = 0.5 * screenSize.width
    minHeight = 0.5 * screenSize.height
    scene = new PlayerSelection(this, gameData, screenSize.width, screenSize.height, graphicMap)
    fullScreenExitHint = "Press esc to leave full screen mode"
  }

  stage.getScene.setOnKeyPressed(
    key => if (key.getCode == KeyCode.F11) {
      stage.setFullScreen(true)
    }
  )

}