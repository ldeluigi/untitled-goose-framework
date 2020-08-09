package engine.events.root

import model.Tile

trait TileEvent extends GameEvent {

  def source: Tile

  override def toString: String = super.toString + " Source: " + source.toString
}

object TileEvent {
  def unapply(arg: TileEvent): Option[Tile] = Some(arg.source)
}
