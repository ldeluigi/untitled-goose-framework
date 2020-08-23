package model.game

import engine.events.root.GameEvent
import model.Player
import model.entities.board.Piece

/** Models the game's state concept. */
trait GameState {

  /**
   * @return the value representing the game being started or not.
   */
  def newTurnStarted: Boolean

  /**
   * @return the current turn index
   */
  def currentTurn: Int

  /**
   * @return the current player playing
   */
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

  /**
   * @return the history of past game events
   */
  def history: List[GameEvent]

  /**
   * @return a set of players pieces
   */
  def players: Set[Player] = playerPieces.keySet

}
