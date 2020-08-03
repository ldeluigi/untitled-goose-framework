package engine.events.root

import model.{Tile}

abstract class TileEvent(tile: Tile, currentTurn: Long) extends AbstractGameEvent(currentTurn) {

  def source: Tile = tile

}

abstract class ConsumableTileEvent(tile: Tile, currentTurn: Long, consumeTimes: Int) extends ConsumableGameEvent(currentTurn, consumeTimes){

  def source: Tile = tile

}

object TileEvent {
  def unapply(arg: TileEvent): Option[Tile] = Some(arg.source)
}
