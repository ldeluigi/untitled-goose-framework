package model.actions

import model.entities.board.Position
import model.{MatchState, Tile}

class StepForwardAction() extends Action {

  override def name: String = "Move Forward"

  override def execute(gameState: MatchState): MatchState = {
    var piece = gameState.playerPieces(gameState.currentPlayer)
    var nextTile = findNextRec(piece.position.tile, gameState.matchBoard.tiles)
    if (nextTile.isDefined) {
      piece.setPosition(Position(nextTile.get))
    }
    gameState
  }

  @scala.annotation.tailrec
  private def findNextRec(currentTile: Tile, tiles: List[Tile]): Option[Tile] = tiles match {
    case Nil => None
    case _ => {
      if (tiles.head == currentTile) {
        tiles.tail match {
          case Nil => None
          case _ => Some(tiles.tail.head)
        }
      } else {
        findNextRec(currentTile, tiles.tail)
      }
    }
  }


}
