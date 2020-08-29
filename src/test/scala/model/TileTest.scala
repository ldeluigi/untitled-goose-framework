package model

import model.entities.board.TileDefinition
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TileTest extends AnyFlatSpec with Matchers {

  behavior of "TileTest"

  val tile1: Tile = Tile(TileDefinition(1))
  val tile2: Tile = Tile(TileDefinition(1))

  it should "have a history of happened tiles events" in {
    tile1.history should have size 0
  }
}
