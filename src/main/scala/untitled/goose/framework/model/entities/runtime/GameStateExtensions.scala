package untitled.goose.framework.model.entities.runtime

import untitled.goose.framework.model.entities.definitions.TileIdentifier.Group
import untitled.goose.framework.model.entities.runtime.functional.GameStateUpdate.GameStateUpdateImpl
import untitled.goose.framework.model.events._
import untitled.goose.framework.model.events.consumable.{ConsumableGameEvent, StopOnTileEvent}
import untitled.goose.framework.model.events.persistent.{PersistentGameEvent, TurnEndedEvent}
import untitled.goose.framework.model.events.special.{ExitEvent, NoOpEvent}

import scala.reflect.ClassTag

/** Helper methods for game state creation or manipulation. */
object GameStateExtensions {

  /** Pimps a game state object. */
  implicit class GameStateExtensions(state: GameState) {

    /**
     * Returns a tile with specified number from the board.
     *
     * @param number the tile number.
     * @return the tile, if found, as an option.
     */
    def getTile(number: Int): Option[Tile] =
      state.gameBoard.tiles.values.find(t => t.definition.number.isDefined && t.definition.number.get == number)

    /**
     * Returns a tile with specified name from the board.
     *
     * @param name the tile name.
     * @return the tile, if found, as an option.
     */
    def getTile(name: String): Option[Tile] =
      state.gameBoard.tiles.values.find(t => t.definition.name.isDefined && t.definition.name.get == name)


    def getFirstTileOf(group: Group): Option[Tile] =
      state.gameBoard.tiles.values.toList.sorted.find(t => t.definition.belongsTo(group.name))

    /**
     * Returns a sequence of values that represent the turns at which a player stopped its
     * movement on the given tile.
     *
     * @param tile   the tile to query.
     * @param player the player that could have stopped on the tile.
     * @return the turns at which the player stopped on the tile.
     */
    def playerStopOnTileTurns(tile: Tile, player: Player): Seq[Int] = tile.history
      .only[StopOnTileEvent]
      .filter(_.player.equals(player))
      .map(_.turn)

    /** Returns the last turn during which given player was active. */
    def playerLastTurn(player: Player): Option[Int] = player.history
      .filter(_.isInstanceOf[TurnEndedEvent])
      .map(_.turn).reduceOption(_ max _)


    /**
     * Adds a given event to proper histories, based on the event type.
     *
     * @param event the event to add to the state.
     */
    def submitEvent(event: GameEvent): GameState = event match {
      case NoOpEvent | ExitEvent => state
      case event: ConsumableGameEvent => state.updateConsumableBuffer(event +: _)
      case event: PersistentGameEvent => saveToProperHistories(event)
      case event => state.updateGameHistory(event +: _)
    }

    /**
     * Forces a permanent save of a consumable game event in proper histories, based on event type.
     *
     * @param event the consumable event to save.
     */
    def saveEvent(event: ConsumableGameEvent): GameState = {
      GameStateExtensions(state.updateGameHistory(event +: _)).saveToProperHistories(event)
    }

    private def saveToProperHistories(event: GameEvent): GameState = event match {
      case event: PlayerEvent with TileEvent =>
        state.updateTileHistory(event.tile, event +: _)
          .updatePlayerHistory(event.player, event +: _)
      case event: PlayerEvent => state.updatePlayerHistory(event.player, event +: _)
      case event: TileEvent => state.updateTileHistory(event.tile, event +: _)
    }

  }

  /** Pimps a history of events. */
  implicit class PimpedHistory[H <: GameEvent](history: Seq[H]) {
    /** A type alias for a history. */
    type History = Seq[H]

    /** Filters events different from the given one. */
    def excludeEvent(event: GameEvent): History = history.filterNot(_ == event)

    /** Filters events that are not in the first n of given type. */
    def skipOfType[T: ClassTag](n: Int = 1): History = {
      val exclude = history.only[T].take(n)
      history.filterNot(exclude.contains(_))
    }

    /** Filters events that are not of given type. */
    def excludeEventType[T: ClassTag](): History =
      history.filter({
        case _: T => false
        case _ => true
      })

    /** Filters only events of given turn. */
    def filterTurn(turn: Int): History = history.filter(_.turn == turn)

    /** Filters only events of given cycle. */
    def filterCycle(cycle: Int): History = history.filter(_.cycle == cycle)

    /** Filters only events of given name. */
    def filterName(name: String): History = history.filter(_.name == name)

    /** Filters only events that are of given type and converts the sequence to that
     * generic type.
     */
    def only[T](implicit classTag: ClassTag[T]): Seq[T] = history.filter({
      case _: T => true
      case _ => false
    }).map(_.asInstanceOf[T])
  }

}
