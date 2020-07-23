package model

import engine.`match`.MatchBoard
import model.entities.board.Piece

trait MatchState {
  def currentTurn: Int

  def currentPlayer: Player

  def playerPieces: Map[Player, Piece]

  def matchBoard: MatchBoard

  def history: List[GameEvent]

  def updatePlayerPiece(player: Player, update: Piece =>  Piece): MatchState
}

object MatchState {

  private class MatchStateImpl(startTurn: Int, startPlayer: Player, pieces: Map[Player, Piece],
                               val matchBoard: MatchBoard, val history: List[GameEvent] = List()) extends MatchState {

    val currentTurn: Int = startTurn

    val currentPlayer: Player = startPlayer

    val playerPieces: Map[Player, Piece] = pieces

    override def updatePlayerPiece(player: Player, update: Piece =>  Piece): MatchState =
      new MatchStateImpl(startTurn, startPlayer, playerPieces + (player -> update(playerPieces(player))), matchBoard)
  }

  def apply(startTurn: Int, startPlayer: Player, pieces: Map[Player, Piece], board: MatchBoard): MatchState =
    new MatchStateImpl(startTurn, startPlayer, pieces, board)
}

