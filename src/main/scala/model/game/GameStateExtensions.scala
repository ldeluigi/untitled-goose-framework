package model.game

import engine.events.root.GameEvent
import model.Tile

object GameStateExtensions {

  implicit class MatchStateExtensions(state: GameState) {

    def getTile(number: Int): Option[Tile] = {
      state.gameBoard.tiles.find(t => t.number.isDefined && t.number.get == number)
    }

    def getTile(name: String): Option[Tile] = {
      state.gameBoard.tiles.find(t => t.name.isDefined && t.name.get == name)
    }
  }

  implicit class PimpedHistory[H <: GameEvent](history: List[H]) {
    type History = List[H]
    def filterCurrentTurn(state: GameState): History = history.filter(_.turn == state.currentTurn)
    def filterNotConsumed(): History = history.filterNot(_.isConsumed)
    def consumeAll(): History = history.map(e => {
      e.consume()
      e
    })
  }
}