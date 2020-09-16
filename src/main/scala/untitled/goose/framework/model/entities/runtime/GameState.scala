package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

/** The game state. */
trait GameState {

  /** Returns the current turn of the game. Starts at 0. */
  def currentTurn: Int

  /** Returns the current rule-evaluation cycle. Starts at 0. */
  def currentCycle: Int

  /** Returns the current player. */
  def currentPlayer: Player

  /** Returns the player that is supposed to go next. */
  def nextPlayer: Player

  /** Returns the player-piece map. */
  def playerPieces: Map[Player, Piece]

  /** Returns the current board state and definition. */
  def gameBoard: Board

  /** Returns the current buffer for consumable game events. */
  def consumableBuffer: Seq[ConsumableGameEvent]

  /** Returns the history of permanently saved events. */
  def gameHistory: Seq[GameEvent]

  /** Returns the set of players. */
  def players: Seq[Player]

  /** Makes an immutable copy of this game state. */
  override def clone(): GameState = CloneGameState(this)
}

