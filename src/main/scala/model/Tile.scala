package model

import model.entities.board.TileDefinition

trait Tile extends TileDefinition {
  def history: List[GameEvent] //TODO change
}

object Tile {

  private class TileImpl(tile: TileDefinition) extends Tile {

    override def history: List[GameEvent] = ???

    override def number: Option[Int] = tile.number

    override def name: Option[String] = tile.name

    override def tileType: Option[List[String]] = tile.tileType
  }

  def apply(tileDefinition: TileDefinition): Tile = new TileImpl(tileDefinition)
}
