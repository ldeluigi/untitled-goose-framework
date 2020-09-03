package model.entities.runtime

import model.events._
import model.events.consumable.{ConsumableGameEvent, StopOnTileEvent}
import model.events.persistent.{PersistentGameEvent, TurnEndedEvent}
import model.events.special.{ExitEvent, NoOpEvent}

import scala.reflect.ClassTag

object GameStateExtensions {

  implicit class GameStateExtensions(state: GameState) {

    def getTile(number: Int): Option[Tile] =
      state.gameBoard.tiles.find(t => t.definition.number.isDefined && t.definition.number.get == number)

    def getTile(name: String): Option[Tile] =
      state.gameBoard.tiles.find(t => t.definition.name.isDefined && t.definition.name.get == name)

    def playerStopsTurns(tile: Tile, player: Player): Seq[Int] = tile.history
      .only[StopOnTileEvent]
      .filter(_.player.equals(player))
      .map(_.turn)

    def playerLastTurn(player: Player): Option[Int] = player.history
      .filter(_.isInstanceOf[TurnEndedEvent])
      .map(_.turn).reduceOption(_ max _)
  }

  implicit class MutableStateExtensions(mutable: MutableGameState) extends GameStateExtensions(mutable) {

    def submitEvent(event: GameEvent): Unit = event match {
      case NoOpEvent | ExitEvent => Unit
      case event: ConsumableGameEvent => mutable.consumableBuffer = event +: mutable.consumableBuffer
      case event: PersistentGameEvent => caseMatch(event)
      case event => mutable.gameHistory = event +: mutable.gameHistory
    }

    def saveEvent(event: ConsumableGameEvent): Unit = {
      mutable.gameHistory = event +: mutable.gameHistory
      caseMatch(event)
    }

    private def caseMatch(event: GameEvent): Unit = event match {
      case event: PlayerEvent with TileEvent =>
        event.player.history = event +: event.player.history
        event.tile.history = event +: event.tile.history
      case event: PlayerEvent => event.player.history = event +: event.player.history
      case event: TileEvent => event.tile.history = event +: event.tile.history
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

    def filterCycle(cycle: Int): History = history.filter(_.cycle == cycle)

    def filterName(name: String): History = history.filter(_.name == name)

    def only[T: ClassTag]: Seq[T] = history.filter({
      case _: T => true
      case _ => false
    }).map(_.asInstanceOf[T])
  }

}
