package engine.events.root

import model.Tile

abstract class TileEvent(tile: Tile, currentTurn: Long, consumeTimes: Int = 1) extends AbstractGameEvent(currentTurn, consumeTimes) {

  def source: Tile = tile

}

object TileEvent {
  def unapply(arg: TileEvent): Option[Tile] = Some(arg.source)
}
