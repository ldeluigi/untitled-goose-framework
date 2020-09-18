package untitled.goose.framework.view.scalafx

import java.awt.{Dimension, Toolkit}

import javafx.scene.input.KeyCode
import scalafx.application.JFXApp
import scalafx.scene.image.Image
import untitled.goose.framework.model.entities.definitions.{GameDefinition, TileIdentifier}
import untitled.goose.framework.view.GraphicDescriptor
import untitled.goose.framework.view.scalafx.playerselection.IntroMenu

/** Main ScalaFX launching class.
 *
 * @param gameData   the game's data of the game.
 * @param graphicMap the map containing graphic properties details.
 */
class View(gameData: GameDefinition, graphicMap: Map[TileIdentifier, GraphicDescriptor]) extends JFXApp {
  /** Screen dimension from the default toolkit. */
  val screenSize: Dimension = Toolkit.getDefaultToolkit.getScreenSize

  /** The title of the application. */
  val appTitle: String = gameData.board.name

  stage = new JFXApp.PrimaryStage {
    title.value = appTitle
    width = 0.5 * screenSize.width
    height = 0.5 * screenSize.height
    resizable = true
    minWidth = 0.5 * screenSize.width
    minHeight = 0.5 * screenSize.height
    scene = new IntroMenu(this, gameData, appTitle, screenSize.width, screenSize.height, graphicMap)
    fullScreenExitHint = "Press esc to leave full screen mode"
  }

  stage.getIcons.add(new Image("GooseLogo.png"))

  stage.getScene.setOnKeyPressed(
    key => if (key.getCode == KeyCode.F11) {
      stage setFullScreen true
    }
  )
}
