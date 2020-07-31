package engine.events.root

import model.Tile

abstract class TileEvent(tile: Tile, currentTurn: Long) extends AbstractGameEvent(currentTurn) {

  def source: Tile = tile

}

object TileEvent {
  def unapply(arg: TileEvent): Option[Tile] = Some(arg.source)
}
