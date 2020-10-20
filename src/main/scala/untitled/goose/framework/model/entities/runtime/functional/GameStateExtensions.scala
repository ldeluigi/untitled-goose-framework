package untitled.goose.framework.model.entities.runtime.functional

import untitled.goose.framework.model.entities.definitions.TileDefinition
import untitled.goose.framework.model.entities.definitions.TileIdentifier.Group
import untitled.goose.framework.model.entities.runtime.functional.HistoryExtensions.PimpedHistory
import untitled.goose.framework.model.entities.runtime.{GameState, Player, PlayerDefinition, Tile}
import untitled.goose.framework.model.events.consumable.StopOnTileEvent
import untitled.goose.framework.model.events.persistent.TurnEndedEvent

/** Helper methods for game state creation or manipulation. */
object GameStateExtensions {

  /** Pimps a game state object. */
  implicit class PimpedGameState(state: GameState) {

    /**
     * Returns a tile with specified number from the board.
     *
     * @param number the tile number.
     * @return the tile, if found, as an option.
     */
    def getTile(number: Int): Option[Tile] =
      state
        .gameBoard
        .tiles
        .values
        .find(t => t.definition.number.isDefined && t.definition.number.get == number)

    /**
     * Returns a tile with specified name from the board.
     *
     * @param name the tile name.
     * @return the tile, if found, as an option.
     */
    def getTile(name: String): Option[Tile] =
      state
        .gameBoard
        .tiles
        .values
        .find(t => t.definition.name.isDefined && t.definition.name.get == name)


    def getFirstTileOf(group: Group): Option[Tile] =
      state
        .gameBoard
        .tiles
        .values
        .toList
        .sorted
        .find(t => t.definition.belongsTo(group.name))

    /**
     * Returns a sequence of values that represent the turns at which a player stopped its
     * movement on the given tile.
     *
     * @param tile   the tile to query.
     * @param player the player that could have stopped on the tile.
     * @return the turns at which the player stopped on the tile.
     */
    def playerStopOnTileTurns(tile: TileDefinition, player: PlayerDefinition): Seq[Int] =
      state
        .gameBoard
        .tiles(tile)
        .history
        .only[StopOnTileEvent]
        .filter(_.player == player)
        .map(_.turn)

    /** Returns the last turn during which given player was active. */
    def playerLastTurn(player: PlayerDefinition): Option[Int] =
      state
        .players(player)
        .history
        .filter(_.isInstanceOf[TurnEndedEvent])
        .map(_.turn)
        .reduceOption(_ max _)


    def currentPlayerInstance: Player = state.players(state.currentPlayer)
  }


}
