package model.game

import engine.events.root.GameEvent
import engine.events.{StopOnTileEvent, TurnEndedEvent}
import model.{Player, Tile}

import scala.reflect.ClassTag

/** Models the concept of game state extension */
object GameStateExtensions {

  implicit class MatchStateExtensions(state: GameState) {

    /**
     * @param number the index of the tile to return
     * @return the tile, if present
     */
    def getTile(number: Int): Option[Tile] = {
      state.gameBoard.tiles.find(t => t.number.isDefined && t.number.get == number)
    }

    /**
     * @param name the name of the tile to return
     * @return the tile, if present
     */
    def getTile(name: String): Option[Tile] = {
      state.gameBoard.tiles.find(t => t.name.isDefined && t.name.get == name)
    }

    /**
     * @param tile   the tile on which the players stops its turn
     * @param player the player that stops its turn
     * @return a sequence containing the events triggered by the player who has stopped its turn
     */
    def playerStopsTurns(tile: Tile, player: Player): Seq[Int] = tile.history
      .only[StopOnTileEvent]
      .filter(_.source.equals(player))
      .map(_.turn)

    def playerLastTurn(player: Player): Option[Int] = player.history
      .filter(_.isInstanceOf[TurnEndedEvent])
      .map(_.turn).reduceOption(_ max _)
  }

  implicit class PimpedHistory[H <: GameEvent](history: Seq[H]) {
    type History = Seq[H]

    def filterTurn(turn: Int): History = history.filter(_.turn == turn)

    def filterNotConsumed(): History = history.filterNot(_.isConsumed)

    def consumeAll(): History = history.map(e => {
      e.consume()
      e
    })

    def only[T: ClassTag]: Seq[T] = history.filter({
      case _: T => true
      case _ => false
    }).map(_.asInstanceOf[T])
  }

}
