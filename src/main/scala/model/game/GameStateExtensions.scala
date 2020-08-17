package model.game

import engine.events.{StopOnTileEvent, TurnEndedEvent}
import engine.events.root.GameEvent
import model.{Player, Tile}

import scala.reflect.ClassTag

object GameStateExtensions {

  implicit class MatchStateExtensions(state: GameState) {

    def getTile(number: Int): Option[Tile] = {
      state.gameBoard.tiles.find(t => t.number.isDefined && t.number.get == number)
    }

    def getTile(name: String): Option[Tile] = {
      state.gameBoard.tiles.find(t => t.name.isDefined && t.name.get == name)
    }

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
