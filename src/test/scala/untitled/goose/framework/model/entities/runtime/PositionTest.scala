package untitled.goose.framework.model.entities.runtime

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.model.entities.definitions.TileDefinition

class PositionTest extends AnyFlatSpec with Matchers {

  val tileDefinition: TileDefinition = TileDefinition(1)
  val tile: Tile = Tile(tileDefinition)
  val position: Position = Position(tile.definition)

  "A position" should "have a tile when specified" in {
    position.tile should equal(tile.definition)
  }

}
