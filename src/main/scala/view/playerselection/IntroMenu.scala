package view.playerselection

import model.game.Game
import model.{GameData, TileIdentifier}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control._
import scalafx.scene.layout.{BorderPane, HBox}
import scalafx.scene.paint.Color.DarkGreen
import scalafx.scene.text.Text
import scalafx.stage.Stage
import view.ApplicationController
import view.board.GraphicDescriptor


/** A scene used to be able to add new players to the game.
 *
 * @param stage      the stage on which to render the selection
 * @param gameData   a container of the board definition and ruleSet of the current game
 * @param widthSize  width of the scene
 * @param heightSize height of the scene
 * @param graphicMap the graphic properties container
 */
class IntroMenu(stage: Stage, gameData: GameData, widthSize: Int, heightSize: Int, graphicMap: Map[TileIdentifier, GraphicDescriptor]) extends Scene {
  val borderPane = new BorderPane

  root = borderPane

  val playersPane: InsertPlayerPane = InsertPlayerPane(gameData.playersRange)


  val startGame: Button = new Button {
    text = "Start game!"
    textFill = DarkGreen
    style = "-fx-font-size: 15pt"
  }

  val upperGameNameHeader: HBox = new HBox {
    alignment = Pos.Center
    padding = Insets(30)
    children = new Text {
      text = "Untitled Goose Framework"
      style = "-fx-font-size: 28pt"
    }
  }
  val bottomGameControls: HBox = new HBox {
    alignment = Pos.BottomCenter
    spacing = 15
    padding = Insets(15)
    children = List(startGame)
  }


  startGame.onAction = _ => {
    val minimumNeededPlayers: Int = gameData.playersRange.start
    if (playersPane.checkPlayers) {
      val currentMatch: Game = gameData.createGame(playersPane.getPlayerSeq, playersPane.getPlayersPiecesMap)
      val appView: ApplicationController = ApplicationController(stage, widthSize, heightSize, currentMatch, graphicMap)
      stage.scene = appView
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

}