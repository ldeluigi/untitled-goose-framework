package untitled.goose.framework.model.events

import untitled.goose.framework.model.entities.runtime.Tile

trait TileEvent extends GameEvent {

  def tile: Tile

  override def toString: String = super.toString + " " + tile.toString
}

object TileEvent {
  def unapply(arg: TileEvent): Option[Tile] = Some(arg.tile)
}
