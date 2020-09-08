package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

/** A mutable view of a game state. */
trait MutableGameState extends GameState {

  /** Setter for the current player. */
  def currentPlayer_=(player: Player): Unit

  /** Setter for the current turn. */
  def currentTurn_=(turn: Int): Unit

  /** Setter for the current consumable buffer. */
  def consumableBuffer_=(events: Seq[ConsumableGameEvent]): Unit

  /** Setter for the current game history. */
  def gameHistory_=(events: Seq[GameEvent]): Unit

  /** Utility method to update a player's piece. */
  def updatePlayerPiece(player: Player, update: Piece => Piece): Unit
}
