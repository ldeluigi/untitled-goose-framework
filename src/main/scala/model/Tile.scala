package model

import engine.events.root.TileEvent
import model.entities.board.TileDefinition

trait Tile extends TileDefinition {
  def history: List[TileEvent]

  def history_=(history: List[TileEvent]): Unit
}

object Tile {

  private class TileImpl(tile: TileDefinition) extends Tile {

    var history: List[TileEvent] = List()

    override def number: Option[Int] = tile.number

    override def name: Option[String] = tile.name

    override def tileType: Option[List[String]] = tile.tileType
  }

  def apply(tileDefinition: TileDefinition): Tile = new TileImpl(tileDefinition)
}
