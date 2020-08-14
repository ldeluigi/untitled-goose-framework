package model.`match`

import engine.events.root.GameEvent
import model.Tile

object MatchStateExtensions {

  implicit class MatchStateExtensions(state: MatchState) {

    def getTile(number: Int): Option[Tile] = {
      state.matchBoard.tiles.find(t => t.number.isDefined && t.number.get == number)
    }

    def getTile(name: String): Option[Tile] = {
      state.matchBoard.tiles.find(t => t.name.isDefined && t.name.get == name)
    }
  }

  implicit class PimpedHistory[H <: GameEvent](history: List[H]) {
    type History = List[H]
    def filterCurrentTurn()(implicit state: MatchState): History = history.filter(_.turn == state.currentTurn)
    def filterNotConsumed(): History = history.filterNot(_.isConsumed)
    def consumeAll(): History = history.map(e => {
      e.consume()
      e
    })
  }
}
