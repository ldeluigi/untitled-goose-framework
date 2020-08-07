package model.entities.board

import model.Tile
import org.scalatest.flatspec.AnyFlatSpec

class PositionTest extends AnyFlatSpec {

  val tileDefinition: TileDefinition = TileDefinition(1)
  val tile: Tile = Tile(tileDefinition)
  val position: Position = Position(tile)

  "A position" should "have a tile when specified" in {
    assert(position.tile.equals(tile))
  }

}
