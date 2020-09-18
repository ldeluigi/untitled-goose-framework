package untitled.goose.framework.view.scalafx.playerselection

import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, HBox}
import scalafx.scene.text.Text
import scalafx.stage.Stage
import untitled.goose.framework.model.entities.definitions.{GameDefinition, TileIdentifier}
import untitled.goose.framework.model.entities.runtime.{Game, GameState}
import untitled.goose.framework.view.GraphicDescriptor
import untitled.goose.framework.view.scalafx.{ScalaFxController, ScalaFxGameScene}

import scala.collection.immutable.ListMap


/** A scene used to be able to add new players to the runtime.
 *
 * @param stage      the stage on which to render the selection
 * @param gameData   a container of the definition definition and ruleSet of the current runtime
 * @param boardName  the name of the definition to be displayed in the menu
 * @param widthSize  width of the scene
 * @param heightSize height of the scene
 * @param graphicMap the graphic properties container
 */
class IntroMenu(stage: Stage, gameData: GameDefinition, boardName: String, widthSize: Int, heightSize: Int, graphicMap: Map[TileIdentifier, GraphicDescriptor]) extends Scene {
  private val borderPane = new BorderPane
  root = borderPane
  borderPane.styleClass.add("borderPane")

  private val playersPane: InsertPlayerPane = InsertPlayerPane(gameData.playersRange, stage)

  private val startGame: Button = new Button {
    text = "Start game!"
  }
  startGame.styleClass.add("startGame")

  private val upperGameNameHeader: HBox = new HBox {
    alignment = Pos.Center
    children = new Text {
      text = boardName
    }
  }
  upperGameNameHeader.styleClass.add("upperGameNameHeader")

  private val bottomGameControls: HBox = new HBox {
    alignment = Pos.BottomCenter
    spacing = 15
    children = List(startGame)
  }
  bottomGameControls.styleClass.add("bottomGameControls")

  startGame.onAction = _ => {
    val minimumNeededPlayers: Int = gameData.playersRange.start
    if (playersPane.checkPlayers) {
      val currentMatch: Game = Game(gameData, ListMap(playersPane.getPlayerSeq.map(p => (p, playersPane.getPlayersPiecesMap(p))): _*))
      val clonedState: GameState = currentMatch.currentState.clone()
      val controller: ScalaFxController = ScalaFxController(currentMatch)
      val gameScene: ScalaFxGameScene = ScalaFxGameScene(stage, controller, clonedState, graphicMap)
      controller.setScene(gameScene)
      stage.scene = gameScene
      stage.minWidth = widthSize * 0.5
      stage.minHeight = heightSize * 0.5
      stage setResizable true
      stage setMaximized true
    } else {
      new Alert(AlertType.Error) {
        initOwner(stage)
        title = "Error!"
        headerText = "You need at least " + minimumNeededPlayers + " players to start this game!"
        contentText = "Add " + (minimumNeededPlayers - playersPane.getPlayerSeq.size) + " more players."
      }.showAndWait()
    }
  }
  borderPane.top = upperGameNameHeader
  borderPane.center = playersPane
  borderPane.bottom = bottomGameControls

  this.stylesheets.add("css/styleIntroMenu.css")
  this.stylesheets.add("css/fixedStyleIntroMenu.css")

}
