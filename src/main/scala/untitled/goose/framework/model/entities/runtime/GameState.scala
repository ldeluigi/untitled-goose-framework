package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.entities.runtime.Player.PlayerImpl
import untitled.goose.framework.model.events.GameEvent
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent

import scala.collection.immutable.ListMap

/** The game state. */
trait GameState {


  /** Returns the current turn of the game. Starts at 0. */
  def currentTurn: Int

  /** Returns the current rule-evaluation cycle. Starts at 0. */
  def currentCycle: Int

  /** Returns the current player. */
  def currentPlayer: PlayerDefinition

  /** Returns the player-piece map. */
  def playerPieces: Map[PlayerDefinition, Piece]

  /** Returns the current board state and definition. */
  def gameBoard: Board

  /** Returns the current buffer for consumable game events. */
  def consumableBuffer: Seq[ConsumableGameEvent]

  /** Returns the history of permanently saved events. */
  def gameHistory: Seq[GameEvent]

  /** Returns the set of players. */
  def players: ListMap[PlayerDefinition, Player]

}

object GameState {

  case class GameStateImpl(currentTurn: Int,
                           currentCycle: Int,
                           currentPlayer: PlayerDefinition,
                           playerPieces: Map[PlayerDefinition, Piece],
                           gameBoard: Board,
                           consumableBuffer: Seq[ConsumableGameEvent],
                           gameHistory: Seq[GameEvent],
                           players: ListMap[PlayerDefinition, Player]
                          ) extends GameState {
    require(playerPieces.keys.exists(_ == currentPlayer))
    require(currentCycle >= 0)
    require(playerPieces.keySet == players.keySet)
  }

  def apply(players: Seq[PlayerDefinition],
            first: Seq[Player] => Player,
            pieces: Map[PlayerDefinition, Piece],
            board: Board): GameState = {
    val initialPlayers: ListMap[PlayerDefinition, PlayerImpl] = ListMap(players.map(d => (d, PlayerImpl(d))): _*)
    GameStateImpl(0,
      0,
      first(initialPlayers.values.toSeq).definition,
      pieces,
      board,
      Seq(),
      Seq(),
      initialPlayers)
  }
}

