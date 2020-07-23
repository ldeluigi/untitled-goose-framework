package model.actions

import engine.events.{EventSink, MovementEvent}
import model.GameEvent

class StepForwardAction() extends Action {

  override def name: String = "Move Forward"

  //  override def execute(gameState: MatchState): MatchState = {
  //    val piece = gameState.playerPieces(gameState.currentPlayer)
  //    val nextTile = gameState.matchBoard.next(piece.position.tile)
  //    if (nextTile.isDefined) {
  //      piece.setPosition(Position(nextTile.get))
  //    } else {  //TODO move to view
  //      new Alert(AlertType.Information) {
  //        title = "Game Info"
  //        headerText = "Game Over."
  //        contentText = "You won!"
  //      }.showAndWait()
  //    }
  //    gameState
  //  }
  override def execute(sink: EventSink[GameEvent]): Unit = {
    sink.accept(MovementEvent(1))
  }
}

object StepForwardAction {
  def apply(): StepForwardAction = new StepForwardAction()
}
