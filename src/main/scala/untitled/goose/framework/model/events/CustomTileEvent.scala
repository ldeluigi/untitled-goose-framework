package untitled.goose.framework.model.events

import untitled.goose.framework.model.entities.runtime.Tile

// TODO scaladoc
class CustomTileEvent(turn: Int, cycle: Int, name: String, val tile: Tile) extends CustomGameEvent(turn, cycle, name) with TileEvent

object CustomTileEvent {
  def apply(turn: Int, cycle: Int, name: String, tile: Tile): CustomTileEvent = new CustomTileEvent(turn, cycle, name, tile)
}
