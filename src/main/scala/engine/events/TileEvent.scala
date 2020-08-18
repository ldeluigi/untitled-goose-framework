package engine.events

import model.Tile

trait TileEvent extends GameEvent {

  def tile: Tile

  override def toString: String = super.toString + " Source: " + tile.toString
}

object TileEvent {
  def unapply(arg: TileEvent): Option[Tile] = Some(arg.tile)
}
