package engine.events.root

import model.{GameEvent, Tile}

abstract class TileEvent(groupList: List[String], tile: Tile) extends GameEvent {

  override def group: List[String] = groupList

  def source: Tile = tile
}

object TileEvent {
  def unapply(arg: TileEvent): Option[Tile] = Some(arg.source)
}
