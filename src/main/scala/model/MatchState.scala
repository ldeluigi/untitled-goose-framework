package model

import model.entities.board.{Piece}

trait MatchState {
  def currentTurn: Int

  def currentPlayer: Player

  def playerPieces: Map[Player, Piece]

  def history: List[GameEvent]
}
