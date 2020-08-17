package model.game

import engine.events.root.GameEvent
import model.Player
import model.entities.board.Piece

trait GameState {

  def newTurnStarted: Boolean

  def currentTurn: Int

  def currentPlayer: Player

  def nextPlayer: Player

  def playerPieces: Map[Player, Piece]

  def gameBoard: GameBoard

  def history: List[GameEvent]

  def players: Set[Player] = playerPieces.keySet

}
