package untitled.goose.framework.model.entities.runtime.functional

import untitled.goose.framework.model.entities.definitions.TileDefinition
import untitled.goose.framework.model.entities.definitions.TileIdentifier.Group
import untitled.goose.framework.model.entities.runtime.{GameState, Player, PlayerDefinition, Tile}
import untitled.goose.framework.model.events._
import untitled.goose.framework.model.events.consumable.StopOnTileEvent
import untitled.goose.framework.model.events.persistent.TurnEndedEvent

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
