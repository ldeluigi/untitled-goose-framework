package model

import engine.events.GameEventHandler
import model.entities.board.TileDefinition

trait Tile extends TileDefinition {
  def history: List[GameEvent] //TODO change
}

object Tile {
  def apply(): Tile = TileImpl()
}

case class TileImpl() extends Tile {
  override def history: List[GameEvent] = ???

  override def number: Option[Int] = ???

  override def name: Option[String] = ???

  override def tileType: Option[List[String]] = ???

  override def next: Option[TileDefinition] = ???
}
