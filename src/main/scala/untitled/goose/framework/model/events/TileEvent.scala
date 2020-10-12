package untitled.goose.framework.model.events

import untitled.goose.framework.model.entities.definitions.TileDefinition

/** An event that regards a tile. */
trait TileEvent extends GameEvent {

  /** The tile that this event regarded. */
  def tile: TileDefinition

  override def toString: String = super.toString + " " + tile.toString
}

object TileEvent {

  /** Unapply method to extract the tile of a TileEvent. */
  def unapply(arg: TileEvent): Option[TileDefinition] = Some(arg.tile)
}
