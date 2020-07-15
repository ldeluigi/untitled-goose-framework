package model

import model.entities.board.{Piece}

trait MatchState {
  def currentTurn: Int

  def currentPlayer: Player

  def playerPieces: Map[Player, Piece]

  def history: List[GameEvent]
}

object MatchState {
  def apply(startTurn: Int, startPlayer: Player, pieces: Map[Player, Piece]) = new MatchStateImpl(startTurn, startPlayer, pieces)
}

case class MatchStateImpl(startTurn: Int, startPlayer: Player, pieces: Map[Player, Piece]) extends MatchState {

  val currentTurn: Int = startTurn

  val currentPlayer: Player = startPlayer

  val playerPieces: Map[Player, Piece] = pieces

  override def history: List[GameEvent] = ???
}
