package model.actions

import model.entities.board.Position
import model.{MatchState, Tile}

class StepForwardAction() extends Action {

  override def name: String = "Move Forward"

  override def execute(gameState: MatchState): MatchState = {
    val piece = gameState.playerPieces(gameState.currentPlayer)
    val nextTile = gameState.matchBoard.next(piece.position.tile)
    if (nextTile.isDefined) {
      piece.setPosition(Position(nextTile.get))
    }
    gameState
  }

}
