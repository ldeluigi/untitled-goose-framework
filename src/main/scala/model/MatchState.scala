package model

import model.entities.board.Piece

trait MatchState {
  def currentTurn: Int

  def currentPlayer: Player

  def playerPieces: Map[Player, Piece]

  def boardTiles: List[Tile]

  def history: List[GameEvent]
}

object MatchState {
  def apply(startTurn: Int, startPlayer: Player, pieces: Map[Player, Piece], boardTiles: List[Tile]): MatchState =
    MatchStateImpl(startTurn, startPlayer, pieces, boardTiles)
}

case class MatchStateImpl(startTurn: Int, startPlayer: Player, pieces: Map[Player, Piece], boardTiles: List[Tile]) extends MatchState {

  val currentTurn: Int = startTurn

  val currentPlayer: Player = startPlayer

  val playerPieces: Map[Player, Piece] = pieces

  override def history: List[GameEvent] = ???
}
