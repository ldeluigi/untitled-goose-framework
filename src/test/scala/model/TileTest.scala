package model

import model.entities.board.TileDefinition
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TileTest extends AnyFlatSpec with Matchers {

  behavior of "TileTest"

  val tile: Tile = Tile(TileDefinition(1))

  it should "have a history of happened tiles events" in {
    tile.history should have size 0
  }
}
