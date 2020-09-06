package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

trait GameState {

  def currentTurn: Int

  def currentCycle: Int

  def currentPlayer: Player

  def nextPlayer: Player

  def playerPieces: Map[Player, Piece]

  /**
   * @return the runtime board
   */
  def gameBoard: GameBoard

  def consumableBuffer: Seq[ConsumableGameEvent]

  def gameHistory: Seq[GameEvent]

  def players: Set[Player] = playerPieces.keySet

  override def clone(): GameState = ClonedGameState(this)

}

