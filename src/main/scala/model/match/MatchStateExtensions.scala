package model.`match`

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

}
