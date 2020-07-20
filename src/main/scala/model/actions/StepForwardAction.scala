package model.actions

import model.entities.board.Position
import model.{MatchState}
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

class StepForwardAction() extends Action {

  override def name: String = "Move Forward"

  override def execute(gameState: MatchState): MatchState = {
    val piece = gameState.playerPieces(gameState.currentPlayer)
    val nextTile = gameState.matchBoard.next(piece.position.tile)
    if (nextTile.isDefined) {
      piece.setPosition(Position(nextTile.get))
    } else {  //TODO move to view
      new Alert(AlertType.Information) {
        title = "Game Info"
        headerText = "Game Over."
        contentText = "You won!"
      }.showAndWait()
    }
    gameState
  }

}

object StepForwardAction {
  def apply(): StepForwardAction = new StepForwardAction()
}
