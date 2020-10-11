package untitled.goose.framework.model.entities.runtime.functional

import untitled.goose.framework.model.entities.runtime.GameState.GameStateImpl
import untitled.goose.framework.model.entities.runtime.functional.BoardUpdate.BoardUpdateImpl
import untitled.goose.framework.model.entities.runtime.{GameState, Piece, Player, Tile}
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent
import untitled.goose.framework.model.events.{GameEvent, PlayerEvent, TileEvent}

trait GameStateUpdate {

  /** Utility method to update a player's piece. */
  def updatePlayerPiece(player: Player, update: Piece => Piece): GameState

  def updateConsumableBuffer(update: Seq[ConsumableGameEvent] => Seq[ConsumableGameEvent]): GameState

  def updateGameHistory(update: Seq[GameEvent] => Seq[GameEvent]): GameState

  def updateTileHistory(tile: Tile, update: Seq[TileEvent] => Seq[TileEvent]): GameState

  def updatePlayerHistory(player: Player, update: Seq[PlayerEvent] => Seq[PlayerEvent]): GameState

  def updateCurrentCycle(update: Int => Int): GameState

  def updateCurrentTurn(update: Int => Int): GameState

  def updateCurrentPlayer(player: (Player, Seq[Player]) => Player): GameState
}

object GameStateUpdate {
  implicit def from(gameState: GameState): GameStateImpl =
    GameStateImpl(gameState.currentTurn,
      gameState.currentCycle,
      gameState.currentPlayer,
      gameState.playerPieces,
      gameState.gameBoard,
      gameState.consumableBuffer,
      gameState.gameHistory,
      gameState.players)

  implicit class GameStateUpdateImpl(gameState: GameState) extends GameStateUpdate {

    override def updatePlayerPiece(player: Player, update: Piece => Piece): GameState =
      gameState.copy(playerPieces = gameState.playerPieces + (player -> update(gameState.playerPieces(player))))

    override def updateConsumableBuffer(update: Seq[ConsumableGameEvent] => Seq[ConsumableGameEvent]): GameState =
      gameState.copy(consumableBuffer = update(gameState.consumableBuffer))

    override def updateGameHistory(update: Seq[GameEvent] => Seq[GameEvent]): GameState =
      gameState.copy(gameHistory = update(gameState.gameHistory))

    override def updateTileHistory(tile: Tile, update: Seq[TileEvent] => Seq[TileEvent]): GameState =
      gameState.copy(gameBoard = gameState.gameBoard.updateTileHistory(tile, update))

    override def updatePlayerHistory(player: Player, update: Seq[PlayerEvent] => Seq[PlayerEvent]): GameState = ???

    override def updateCurrentCycle(update: Int => Int): GameState = ???

    override def updateCurrentTurn(update: Int => Int): GameState = ???

    override def updateCurrentPlayer(player: (Player, Seq[Player]) => Player): GameState = ???
  }

}
