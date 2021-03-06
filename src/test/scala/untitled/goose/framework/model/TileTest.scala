package untitled.goose.framework.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import untitled.goose.framework.model.entities.definitions.TileDefinition
import untitled.goose.framework.model.entities.runtime.Tile

class TileTest extends AnyFlatSpec with Matchers {

  behavior of "TileTest"

  val tile: Tile = Tile(TileDefinition(1))

  it should "have a history of happened tiles events" in {
    tile.history should have size 0
  }
}
