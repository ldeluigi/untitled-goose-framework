package model.game

import engine.events._
import engine.events.consumable.{ConsumableGameEvent, StopOnTileEvent}
import engine.events.persistent.PersistentGameEvent
import engine.events.persistent.player.TurnEndedEvent
import model.{Player, Tile}

import scala.reflect.ClassTag

object GameStateExtensions {

  implicit class GameStateExtensions(state: GameState) {

    def getTile(number: Int): Option[Tile] =
      state.gameBoard.tiles.find(t => t.number.isDefined && t.number.get == number)

    def getTile(name: String): Option[Tile] =
      state.gameBoard.tiles.find(t => t.name.isDefined && t.name.get == name)

    def playerStopsTurns(tile: Tile, player: Player): Seq[Int] = tile.history
      .only[StopOnTileEvent]
      .filter(_.player.equals(player))
      .map(_.turn)

    def playerLastTurn(player: Player): Option[Int] = player.history
      .filter(_.isInstanceOf[TurnEndedEvent])
      .map(_.turn).reduceOption(_ max _)
  }

  implicit class MutableStateExtensions(mutable: MutableGameState) extends GameStateExtensions(mutable) {

    def submitEvent(event: GameEvent): Unit =
      event match {
        case NoOpEvent | ExitEvent => Unit
        case event: ConsumableGameEvent => mutable.consumableEvents = event +: mutable.consumableEvents
        case event: PersistentGameEvent => caseMatch(event)
      }

    def saveEvent(event: ConsumableGameEvent): Unit =
      caseMatch(event)

    private def caseMatch(event: GameEvent): Unit =
      event match {
        case event: PlayerEvent with TileEvent =>
          event.player.history = event +: event.player.history
          event.tile.history = event +: event.tile.history
        case event: PlayerEvent => event.player.history = event +: event.player.history
        case event: TileEvent => event.tile.history = event +: event.tile.history
        case event: GameEvent => mutable.gameHistory = event +: mutable.gameHistory
      }

  }

  implicit class PimpedHistory[H <: GameEvent](history: Seq[H]) {
    type History = Seq[H]

    def removeEvent(event: GameEvent): History = history.filterNot(_ == event)

    def remove[T: ClassTag](n: Int = 1): History =
      history.filterNot(e => history.only[T].take(n).contains(e))

    def removeAll[T: ClassTag](): History =
      history.filterNot(e => history.only[T].contains(e))

    def filterTurn(turn: Int): History = history.filter(_.turn == turn)

    def filterName(name: String): History = history.filter(_.name == name)

    def filterCycle(cycle: Int): History = history.filter(_.cycle == cycle)

    def only[T: ClassTag]: Seq[T] = history.filter({
      case _: T => true
      case _ => false
    }).map(_.asInstanceOf[T])
  }

}
