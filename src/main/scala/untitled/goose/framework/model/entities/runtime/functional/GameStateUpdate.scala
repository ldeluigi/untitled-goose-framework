package untitled.goose.framework.model.entities.runtime.functional

import untitled.goose.framework.model.entities.definitions.TileDefinition
import untitled.goose.framework.model.entities.runtime.GameState.GameStateImpl
import untitled.goose.framework.model.entities.runtime.Player.PlayerImpl
import untitled.goose.framework.model.entities.runtime.functional.BoardUpdate.BoardUpdateImpl
import untitled.goose.framework.model.entities.runtime.{GameState, Piece, Player, PlayerDefinition}
import untitled.goose.framework.model.events.consumable.ConsumableGameEvent
import untitled.goose.framework.model.events.persistent.PersistentGameEvent
import untitled.goose.framework.model.events.special.{ExitEvent, NoOpEvent}
import untitled.goose.framework.model.events.{GameEvent, PlayerEvent, TileEvent}

trait GameStateUpdate {

  /** Utility method to update a player's piece. */
  def updatePlayerPiece(player: PlayerDefinition, update: Piece => Piece): GameState

  def updateConsumableBuffer(update: Seq[ConsumableGameEvent] => Seq[ConsumableGameEvent]): GameState

  def updateGameHistory(update: Seq[GameEvent] => Seq[GameEvent]): GameState

  def updateTileHistory(tile: TileDefinition, update: Seq[TileEvent] => Seq[TileEvent]): GameState

  def updatePlayerHistory(player: PlayerDefinition, update: Seq[PlayerEvent] => Seq[PlayerEvent]): GameState

  def updateCurrentCycle(update: Int => Int): GameState

  def updateCurrentTurn(update: Int => Int): GameState

  def updateCurrentPlayer(player: (Player, Seq[Player]) => Player): GameState

  /**
   * Adds a given event to proper histories, based on the event type.
   *
   * @param event the event to add to the state.
   */
  def submitEvent(event: GameEvent): GameState

  /**
   * Forces a permanent save of a consumable game event in proper histories, based on event type.
   *
   * @param event the consumable event to save.
   */
  def saveEvent(event: ConsumableGameEvent): GameState
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

    override def updateConsumableBuffer(update: Seq[ConsumableGameEvent] => Seq[ConsumableGameEvent]): GameState =
      gameState.copy(consumableBuffer =
        update(gameState.consumableBuffer))

    override def updateGameHistory(update: Seq[GameEvent] => Seq[GameEvent]): GameState =
      gameState.copy(gameHistory =
        update(gameState.gameHistory))

    override def updatePlayerPiece(player: PlayerDefinition, update: Piece => Piece): GameState =
      gameState.copy(playerPieces =
        gameState.playerPieces + (player -> update(gameState.playerPieces(player))))

    override def updateTileHistory(tile: TileDefinition, update: Seq[TileEvent] => Seq[TileEvent]): GameState =
      gameState.copy(gameBoard =
        gameState.gameBoard.updateTileHistory(tile, update))

    override def updatePlayerHistory(player: PlayerDefinition, update: Seq[PlayerEvent] => Seq[PlayerEvent]): GameState =
      gameState.copy(players =
        gameState.players + (player -> PlayerImpl(player, update(gameState.players(player).history))))

    override def updateCurrentCycle(update: Int => Int): GameState =
      gameState.copy(currentCycle =
        update(gameState.currentCycle))

    override def updateCurrentTurn(update: Int => Int): GameState =
      gameState.copy(currentTurn =
        update(gameState.currentTurn))

    override def updateCurrentPlayer(player: (Player, Seq[Player]) => Player): GameState =
      gameState.copy(currentPlayer =
        player(gameState.players(gameState.currentPlayer), gameState.players.values.toSeq).definition)

    override def submitEvent(event: GameEvent): GameState = event match {
      case NoOpEvent | ExitEvent => gameState
      case event: ConsumableGameEvent => gameState.updateConsumableBuffer(event +: _)
      case event: PersistentGameEvent => saveToProperHistories(gameState, event)
      case event => gameState.updateGameHistory(event +: _)
    }

    override def saveEvent(event: ConsumableGameEvent): GameState =
      saveToProperHistories(gameState.updateGameHistory(event +: _), event)

    private def saveToProperHistories(gameState: GameState, event: GameEvent): GameState = event match {
      case event: PlayerEvent with TileEvent =>
        gameState.updateTileHistory(event.tile, event +: _)
          .updatePlayerHistory(event.player, event +: _)
      case event: PlayerEvent => gameState.updatePlayerHistory(event.player, event +: _)
      case event: TileEvent => gameState.updateTileHistory(event.tile, event +: _)
    }
  }

}
