package model.game

import engine.events.GameEvent
import engine.events.consumable.ConsumableGameEvent
import model.Player
import model.entities.board.Piece

/** Models the game's state concept. */
trait GameState {

  def currentTurn: Int

  def currentCycle: Int

  def currentPlayer: Player

  /**
   * @return the next player playing
   */
  def nextPlayer: Player

  /**
   * @return the map association between players and their respective pieces
   */
  def playerPieces: Map[Player, Piece]

  /**
   * @return the game board
   */
  def gameBoard: GameBoard

  def consumableBuffer: Seq[ConsumableGameEvent]

  def gameHistory: Seq[GameEvent]

  /**
   * @return a set of players pieces
   */
  def players: Set[Player] = playerPieces.keySet

}
