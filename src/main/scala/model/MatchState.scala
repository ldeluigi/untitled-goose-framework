package model

import model.entities.board.Position

trait MatchState {
  def currentTurn: Int

  def currentPlayer: Player

  def playerPositions: Map[Player, Position]

  def history: List[GameEvent]
}
