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

}

object GameState {

  case class GameStateImpl(currentTurn: Int,
                           currentCycle: Int,
                           currentPlayer: Player,
                           playerPieces: Map[Player, Piece],
                           gameBoard: Board,
                           consumableBuffer: Seq[ConsumableGameEvent],
                           gameHistory: Seq[GameEvent],
                           players: Seq[Player]
                          ) extends GameState {
    require(playerPieces.keys.exists(_ == currentPlayer))
    require(currentCycle >= 0)
    require(playerPieces.keySet == players.toSet)
  }

  def apply(players: Seq[Player],
            first: Seq[Player] => Player,
            pieces: Map[Player, Piece],
            board: Board): GameState =
    GameStateImpl(0,
      0,
      first(players),
      pieces,
      board,
      Seq(),
      Seq(),
      players)
}

