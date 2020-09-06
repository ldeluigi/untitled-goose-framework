package untitled.goose.framework.model.entities.definitions

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BoardTest extends AnyFlatSpec with Matchers {

  behavior of "BoardTest"

  val tile1: TileDefinition = TileDefinition(1)
  val tile2: TileDefinition = TileDefinition(2)
  val tile3: TileDefinition = TileDefinition(3)
  val tiles: Set[TileDefinition] = Set(tile1, tile2, tile3)
  val name: String = "Board"
  val disposition: Disposition = Disposition.snake(tiles.size)
  val board: Board = Board(name, tiles, disposition)

  it should "have a name" in {
    board.name should equal(name)
  }

  it should "have a disposition" in {
    board.disposition should equal(disposition)
  }

  it should "have tiles" in {
    board.tiles.size should equal(tiles.size)
  }

  it should "have a first tile" in {
    board.first should equal(tiles.head)
  }

  it can "return the next tile of a given one" in {
    board.next(tile1).get should equal(tile2)
  }

  it can "return the previous tile of a given one" in {
    board.prev(tile2).get should equal(tile1)
  }
}
