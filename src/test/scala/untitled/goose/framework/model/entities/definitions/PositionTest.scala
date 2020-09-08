package untitled.goose.framework.model.entities.definitions

import untitled.goose.framework.model.entities.runtime.{Position, Tile}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PositionTest extends AnyFlatSpec with Matchers {

  val tileDefinition: TileDefinition = TileDefinition(1)
  val tile: Tile = Tile(tileDefinition)
  val position: Position = Position(tile)

  "A position" should "have a tile when specified" in {
    position.tile should equal(tile)
  }

}
