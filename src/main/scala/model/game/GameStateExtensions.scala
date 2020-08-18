package model.game

import engine.events.consumable.{ConsumableGameEvent, StopOnTileEvent}
import engine.events.persistent.PersistentGameEvent
import engine.events.persistent.player.TurnEndedEvent
import engine.events.{GameEvent, PlayerEvent, TileEvent}
import model.{Player, Tile}

import scala.reflect.ClassTag

object GameStateExtensions {

  implicit class MatchStateExtensions(state: MutableGameState) {

    def submitEvent(event: GameEvent): Unit = {
      event match {
        case event: ConsumableGameEvent => state.consumableEvents = event :: state.consumableEvents
        case event: PersistentGameEvent => event match {
          case event: PlayerEvent with TileEvent => {
            event.player.history = event :: event.player.history
            event.tile.history = event :: event.tile.history
          }
          case event: PlayerEvent => event.player.history = event :: event.player.history
          case event: TileEvent => event.tile.history = event :: event.tile.history
          case _ => ???
        }
      }
    }

    def getTile(number: Int): Option[Tile] = {
      state.gameBoard.tiles.find(t => t.number.isDefined && t.number.get == number)
    }

    def getTile(name: String): Option[Tile] = {
      state.gameBoard.tiles.find(t => t.name.isDefined && t.name.get == name)
    }

    def playerStopsTurns(tile: Tile, player: Player): Seq[Int] = tile.history
      .only[StopOnTileEvent]
      .filter(_.player.equals(player))
      .map(_.turn)

    def playerLastTurn(player: Player): Option[Int] = player.history
      .filter(_.isInstanceOf[TurnEndedEvent])
      .map(_.turn).reduceOption(_ max _)
  }

  implicit class PimpedHistory[H <: GameEvent](history: Seq[H]) {
    type History = Seq[H]

    def filterTurn(turn: Int): History = history.filter(_.turn == turn)

    def filterName(name: String): History = history.filter(_.name == name)

    def filterCycle(cycle: Int): History = history.filter(_.cycle == cycle)

    def only[T: ClassTag]: Seq[T] = history.filter({
      case _: T => true
      case _ => false
    }).map(_.asInstanceOf[T])
  }

}
