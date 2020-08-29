package model

import model.entities.board.TileDefinition
import org.scalatest.flatspec.AnyFlatSpec

class TileTest extends AnyFlatSpec {

  val tile: TileDefinition = TileDefinition(1)

  "A tile" should "have a history of happened tiles events" in {
    pending
  }

  it should "be able to compare tiles" in {
    pending
  }
}
