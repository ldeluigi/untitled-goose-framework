package model.game

import engine.events.root.GameEvent
import model.Tile

import scala.reflect.ClassTag

object GameStateExtensions {

  implicit class MatchStateExtensions(state: GameState) {

    def getTile(number: Int): Option[Tile] = {
      state.gameBoard.tiles.find(t => t.number.isDefined && t.number.get == number)
    }

    def getTile(name: String): Option[Tile] = {
      state.gameBoard.tiles.find(t => t.name.isDefined && t.name.get == name)
    }
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
