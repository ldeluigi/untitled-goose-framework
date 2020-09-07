package untitled.goose.framework.model.events

import untitled.goose.framework.model.entities.runtime.Tile

/** An event that regards a tile. */
trait TileEvent extends GameEvent {

  /** The tile that this event regarded. */
  def tile: Tile

  override def toString: String = super.toString + " " + tile.toString
}

object TileEvent {

  /** Unapply method to extract the tile of a TileEvent. */
  def unapply(arg: TileEvent): Option[Tile] = Some(arg.tile)
}
