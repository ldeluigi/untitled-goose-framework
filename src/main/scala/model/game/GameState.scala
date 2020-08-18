package model.game

import engine.events.GameEvent
import model.Player
import model.entities.board.Piece

trait GameState {

  def currentTurn: Int

  def currentCycle: Int

  def currentPlayer: Player

  def nextPlayer: Player

  def playerPieces: Map[Player, Piece]

  def gameBoard: GameBoard

  def consumableEvents: Seq[GameEvent]

  def players: Set[Player] = playerPieces.keySet

}
