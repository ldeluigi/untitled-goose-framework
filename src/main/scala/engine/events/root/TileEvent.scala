package engine.events.root

import model.Tile

abstract class TileEvent(tile: Tile) extends GameEvent {

  override def group: List[String] = List()

  def source: Tile = tile
}

object TileEvent {
  def unapply(arg: TileEvent): Option[Tile] = Some(arg.source)
}
