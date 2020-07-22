package engine.events

import model.{GameEvent, Tile}

class TileEvent(groupList: List[String], tile: Tile) extends GameEvent {

  override def name: String = "TileEvent"

  override def group: List[String] = groupList

  def source: Tile = tile
}

object TileEvent{
  def apply(groupList: List[String], tile: Tile): TileEvent = new TileEvent(groupList, tile)
}


