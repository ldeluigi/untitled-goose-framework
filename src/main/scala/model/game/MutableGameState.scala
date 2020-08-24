package model.game

import engine.events.GameEvent
import engine.events.consumable.ConsumableGameEvent
import model.Player
import model.entities.board.Piece

/** Models the concept of a mutable game state */
trait MutableGameState extends GameState {

  def currentPlayer_=(player: Player): Unit

  /**
   * @param turn the turn to be set as the starting turn
   */
  def currentTurn_=(turn: Int): Unit

  def consumableBuffer_=(events: Seq[ConsumableGameEvent]): Unit

  def gameHistory_=(events: Seq[GameEvent]): Unit

  /**
   * @param player the player owning the piece to update
   * @param update the piece of the player to be updated
   */
  def updatePlayerPiece(player: Player, update: Piece => Piece): Unit
}
